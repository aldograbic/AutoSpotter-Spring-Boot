package com.example.AutoSpotter.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.AutoSpotter.classes.listing.Listing;
import com.example.AutoSpotter.classes.listing.ListingRepository;
import com.example.AutoSpotter.classes.vehicle.Vehicle;
import com.example.AutoSpotter.classes.vehicle.VehicleRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AddListingController {

    private final ListingRepository listingRepository;
    private final VehicleRepository vehicleRepository;

    public AddListingController(ListingRepository listingRepository, VehicleRepository vehicleRepository) {
        this.listingRepository = listingRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("/postavi-oglas")
    public String showNewListing(HttpSession session, Model model) {

        Integer step = (Integer) session.getAttribute("step");
        if (step == null) {
            step = 1;
            session.setAttribute("step", step);
        }
        List<Boolean> completedSteps = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            boolean isCompleted = i < step;
            completedSteps.add(isCompleted);
        }
        List<String> vehicleTypes = vehicleRepository.getAllVehicleTypes();
        List<Integer> years = new ArrayList<>();
        for(int i = 2023; i >= 1900; i--) {
            years.add(i);
        }
        List<String> cities = vehicleRepository.getAllCities();
        List<String> states = vehicleRepository.getAllStates();
        Integer vehicleTypeId = (Integer) session.getAttribute("vehicleTypeId");
        if (vehicleTypeId != null) {
            List<String> manufacturers = vehicleRepository.getManufacturersByVehicleType(vehicleTypeId);
            model.addAttribute("manufacturers", manufacturers);
        }
        List<String> bodyTypes = vehicleRepository.getAllBodyTypes();

        if (step == 2 && 1 == ((Integer) session.getAttribute("vehicleTypeId"))) {
            model.addAttribute("showBodyTypeInput", true);
        } else {
            model.addAttribute("showBodyTypeInput", false);
        }
        List<String> engineTypes = vehicleRepository.getAllEngineTypes();

        model.addAttribute("step", step);
        model.addAttribute("completedSteps", completedSteps);
        model.addAttribute("vehicleTypes", vehicleTypes);  
        model.addAttribute("years", years);  
        model.addAttribute("cities", cities);     
        model.addAttribute("states", states);
        model.addAttribute("bodyTypes", bodyTypes);

        model.addAttribute("engineTypes", engineTypes);

        return "add-listing";
    }

    @PostMapping("/back")
    public String handleBackButton(@RequestParam("step") int step, HttpSession session) {
        session.setAttribute("step", step);
        return "redirect:/postavi-oglas";
    }

    @PostMapping("/oglas-1")
    public String handleStep1FormSubmission(@RequestParam("vehicleType") String vehicleType, HttpSession session, Model model) {
        int vehicleTypeId = vehicleRepository.getVehicleTypeId(vehicleType);
        session.setAttribute("vehicleTypeId", vehicleTypeId);
        session.setAttribute("step", 2);

        return "redirect:/postavi-oglas";
    }

    @PostMapping("/oglas-2")
    public String handleStep2FormSubmission(@RequestParam("manufacturer") String manufacturer,
                                            @RequestParam("model") String vehicleModel,
                                            @RequestParam("bodyType") String bodyType,
                                            @RequestParam("mileage") int mileage,
                                            @RequestParam("location") String location,
                                            @RequestParam("year") int year,
                                            @RequestParam("state") String state,
                                            HttpSession session, Model model) {

        int vehicleTypeId = (int) session.getAttribute("vehicleTypeId");

        List<String> manufacturers = vehicleRepository.getManufacturersByVehicleType(vehicleTypeId);
        session.setAttribute("manufacturers", manufacturers);

        session.setAttribute("manufacturer", manufacturer);
        session.setAttribute("model", vehicleModel);
        session.setAttribute("bodyType", bodyType);
        session.setAttribute("mileage", mileage);
        session.setAttribute("location", location);
        session.setAttribute("year", year);
        session.setAttribute("state", state);

        session.setAttribute("step", 3);
        return "redirect:/postavi-oglas";
    }

    @PostMapping("/oglas-3") //tehnicki detalji
    public String handleStep3FormSubmission(@RequestParam("engineType") String engineType,
                                            @RequestParam("engineDisplacement") String engineDisplacement,
                                            @RequestParam("enginePower") int enginePower,
                                            @RequestParam("fuelConsumption") String fuelConsumption,
                                            @RequestParam("transmission") String transmission,
                                            HttpSession session, Model model) {

        String manufacturer = (String) session.getAttribute("manufacturer");
        String vehicleModel = (String) session.getAttribute("model");
        String bodyType = (String) session.getAttribute("bodyType");
        int mileage = (int) session.getAttribute("mileage");
        String location = (String) session.getAttribute("location");
        int year = (int) session.getAttribute("year");
        String state = (String) session.getAttribute("state");
        int vehicleTypeId = (int) session.getAttribute("vehicleTypeId");

        Vehicle vehicle = new Vehicle(manufacturer, vehicleModel, bodyType, mileage, location, state, year, engineType,
                                     engineDisplacement, enginePower, fuelConsumption, transmission, vehicleTypeId);

        int vehicleId = vehicleRepository.saveVehicle(vehicle);
        session.setAttribute("vehicleId", vehicleId);

        session.setAttribute("step", 4);
        return "redirect:/postavi-oglas";
    }

    @PostMapping("/oglas-4") //detaljni detalji
    public String handleStep4FormSubmission(HttpSession session, Model model) {
        session.setAttribute("step", 5);
        return "redirect:/postavi-oglas";
    }

    @PostMapping("/oglas-5")
    public String handleStep5FormSubmission(@RequestParam("images") MultipartFile[] images, HttpSession session) {
        session.setAttribute("images", images);
        session.setAttribute("step", 6);
        return "redirect:/postavi-oglas";
    }

    @PostMapping("/oglas-6")
    public String handleStep6FormSubmission(@RequestParam("description") String description,
                                            @RequestParam("price") BigDecimal price,
                                            HttpSession session) {

        int vehicleId = (int) session.getAttribute("vehicleId");

        int userId = 1; // Retrieve the userId from the logged-in user

        Listing listing = new Listing();
        listing.setListingDescription(description);
        listing.setListingPrice(price);
        listing.setVehicleId(vehicleId);
        listing.setUserId(userId);

        listingRepository.createListing(listing);

        session.invalidate();
        // ispisat poruku da je uspjesno dodan oglas
        return "redirect:/";
    }
}