package com.example.AutoSpotter.controllers;

import java.math.BigDecimal;
import java.sql.Date;
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

            if (vehicleTypeId == 1) {
                model.addAttribute("showNumberOfWheelsInput", true);
                model.addAttribute("initialNumberOfWheels", 4);
            } else if (vehicleTypeId == 2) {
                model.addAttribute("showNumberOfWheelsInput", true);
                model.addAttribute("initialNumberOfWheels", 2);
            } else if (vehicleTypeId == 3) {
                model.addAttribute("showNumberOfWheelsInput", true);
                model.addAttribute("initialNumberOfWheels", null);
            } else {
                model.addAttribute("showNumberOfWheelsInput", false);
                model.addAttribute("initialNumberOfWheels", null);
            }
        }
        List<String> bodyTypes = vehicleRepository.getAllBodyTypes();

        if (step == 2 && 1 == ((Integer) session.getAttribute("vehicleTypeId"))) {
            model.addAttribute("showBodyTypeInput", true);
        } else {
            model.addAttribute("showBodyTypeInput", false);
        }
        if (step == 2 && 3 == ((Integer) session.getAttribute("vehicleTypeId"))) {
            model.addAttribute("showNumberOfWheelsInput", true);
            model.addAttribute("showMaximumAllowableWeightInput", true);
        } else {
            model.addAttribute("showNumberOfWheelsInput", false);
            model.addAttribute("showMaximumAllowableWeightInput", false);
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
                                            @RequestParam(value = "bodyType", required = false) String bodyType,
                                            @RequestParam(value = "hiddenNumberOfWheels", required = false) String hiddenNumberOfWheels,
                                            @RequestParam(value = "maximumAllowableWeight", required = false) String maximumAllowableWeight,
                                            @RequestParam("year") int year,
                                            @RequestParam("registeredDate") Date registered,
                                            @RequestParam("color") String color,
                                            @RequestParam("city") String city,
                                            @RequestParam("mileage") int mileage,
                                            @RequestParam("state") String state,
                                            HttpSession session, Model model) {

        int vehicleTypeId = (int) session.getAttribute("vehicleTypeId");
        
        int cityId = vehicleRepository.getCityIdByName(city);
        List<String> manufacturers = vehicleRepository.getManufacturersByVehicleType(vehicleTypeId);
        session.setAttribute("manufacturers", manufacturers);

        session.setAttribute("manufacturer", manufacturer);
        session.setAttribute("model", vehicleModel);
        session.setAttribute("bodyType", bodyType);
        session.setAttribute("numberOfWheels", hiddenNumberOfWheels);
        session.setAttribute("maximumAllowableWeight", maximumAllowableWeight);
        session.setAttribute("year", year);
        session.setAttribute("registeredDate", registered);
        session.setAttribute("color", color);
        session.setAttribute("cityId", cityId);
        session.setAttribute("mileage", mileage);
        session.setAttribute("state", state);

        session.setAttribute("step", 3);
        return "redirect:/postavi-oglas";
    }

    @PostMapping("/oglas-3") //tehnicki detalji
    public String handleStep3FormSubmission(@RequestParam("engineType") String engineType,
                                            @RequestParam("engineDisplacement") double engineDisplacement,
                                            @RequestParam("enginePower") int enginePower,
                                            @RequestParam("fuelConsumption") double fuelConsumption,
                                            @RequestParam("transmission") String transmission,
                                            @RequestParam("driveTrain") String driveTrain,
                                            HttpSession session, Model model) {


        session.setAttribute("engineType", engineType);
        session.setAttribute("engineDisplacement", engineDisplacement);
        session.setAttribute("enginePower", enginePower);
        session.setAttribute("fuelConsumption", fuelConsumption);
        session.setAttribute("transmission", transmission);
        session.setAttribute("driveTrain", driveTrain);

        session.setAttribute("step", 4);
        return "redirect:/postavi-oglas";
    }

    @PostMapping("/oglas-4") //detaljni detalji
    public String handleStep4FormSubmission(@RequestParam(value = "batteryCapacity", required = false) Double batteryCapacity,
                                            @RequestParam(value = "vehicleRange", required = false) Double vehicleRange,
                                            @RequestParam(value = "chargingTime", required = false) Double chargingTime,
                                            HttpSession session, Model model) {

        String engineType = (String) session.getAttribute("engineType");

        if ("Električni".equals(engineType)) {
            model.addAttribute("batteryCapacity", batteryCapacity);
            model.addAttribute("vehicleRange", vehicleRange);
            model.addAttribute("chargingTime", chargingTime);
        }

        String manufacturer = (String) session.getAttribute("manufacturer");
        String vehicleModel = (String) session.getAttribute("model");
        String bodyType = (String) session.getAttribute("bodyType");
        int year = (int) session.getAttribute("year");
        String numberOfWheelsStr = (String) session.getAttribute("numberOfWheels");
        int numberOfWheels = Integer.parseInt(numberOfWheelsStr);
        double maximumAllowableWeight = (double) session.getAttribute("maximumAllowableWeight");
        Date registered = (Date) session.getAttribute("registeredDate");
        String color = (String) session.getAttribute("color");
        int mileage = (int) session.getAttribute("mileage");
        String state = (String) session.getAttribute("state");

        double engineDisplacement = (double) session.getAttribute("engineDisplacement");
        int enginePower = (int) session.getAttribute("enginePower");
        double fuelConsumption = (double) session.getAttribute("fuelConsumption");
        String transmission = (String) session.getAttribute("transmission");
        String driveTrain = (String) session.getAttribute("driveTrain");

        int cityId = (int) session.getAttribute("cityId");
        int vehicleTypeId = (int) session.getAttribute("vehicleTypeId");

        Vehicle vehicle = new Vehicle(manufacturer, vehicleModel, bodyType, color, registered, mileage, state, year, numberOfWheels,
                                    maximumAllowableWeight, engineType, engineDisplacement, enginePower, fuelConsumption, transmission,
                                    driveTrain, batteryCapacity, chargingTime, vehicleRange, cityId, vehicleTypeId);

        int vehicleId = vehicleRepository.saveVehicle(vehicle);
        session.setAttribute("vehicleId", vehicleId);

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