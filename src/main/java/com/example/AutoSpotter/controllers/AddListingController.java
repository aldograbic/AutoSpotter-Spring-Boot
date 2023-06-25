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
        model.addAttribute("step", step);

        List<String> vehicleTypes = vehicleRepository.getAllVehicleTypes();
        model.addAttribute("vehicleTypes", vehicleTypes);

        List<Integer> years = new ArrayList<>();
        for(int i = 2023; i >= 1900; i--) {
            years.add(i);
        }
        model.addAttribute("years", years);

        List<String> states = vehicleRepository.getAllStates();
        model.addAttribute("states", states);

        Integer vehicleTypeId = (Integer) session.getAttribute("vehicleTypeId");
        if (vehicleTypeId != null) {
            List<String> manufacturers = vehicleRepository.getManufacturersByVehicleType(vehicleTypeId);
            model.addAttribute("manufacturers", manufacturers);
        }

        return "add-listing";
    }

    @PostMapping("/back")
    public String handleBackButton(@RequestParam("step") int step, HttpSession session) {
        session.setAttribute("step", step);
        return "redirect:/postavi-oglas";
    }

    // Step 1: Vehicle Type form submission
    @PostMapping("/oglas-1")
    public String handleStep1FormSubmission(@RequestParam("vehicleType") String vehicleType, HttpSession session, Model model) {
        int vehicleTypeId = vehicleRepository.getVehicleTypeId(vehicleType);
        session.setAttribute("vehicleTypeId", vehicleTypeId);
        session.setAttribute("step", 2);

        return "redirect:/postavi-oglas";
    }

    // Step 2: Vehicle Details form submission
    @PostMapping("/oglas-2")
    public String handleStep2FormSubmission(@RequestParam("manufacturer") String manufacturer,
                                            @RequestParam("model") String vehicleModel,
                                            @RequestParam("mileage") int mileage,
                                            @RequestParam("year") int year,
                                            @RequestParam("state") String state,
                                            HttpSession session, Model model) {
        int vehicleTypeId = (int) session.getAttribute("vehicleTypeId");

        Vehicle vehicle = new Vehicle(vehicleModel, manufacturer, mileage, state, year, vehicleTypeId);
        int vehicleId = vehicleRepository.saveVehicle(vehicle);
        session.setAttribute("vehicleId", vehicleId);
        session.setAttribute("step", 3);

        if (state.isEmpty()) {
            // Handle the case where no state is selected
            // You can display an error message or perform any other desired action
            // For example, you can redirect back to the form and show an error message
            model.addAttribute("errorMessage", "Please select a state.");
            return "add-listing";
        }

        List<String> manufacturers = vehicleRepository.getManufacturersByVehicleType(vehicleTypeId);
        session.setAttribute("manufacturers", manufacturers);

        return "redirect:/postavi-oglas";
    }


     // Step 3: Vehicle Images form submission
    @PostMapping("/oglas-3")
    public String handleStep3FormSubmission(@RequestParam("images") MultipartFile[] images, HttpSession session) {
        session.setAttribute("images", images);
        session.setAttribute("step", 4);
        return "redirect:/postavi-oglas";
    }

    // Step 4: Listing Title, Description, and Price form submission
    @PostMapping("/oglas-4")
    public String handleStep4FormSubmission(@RequestParam("description") String description,
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