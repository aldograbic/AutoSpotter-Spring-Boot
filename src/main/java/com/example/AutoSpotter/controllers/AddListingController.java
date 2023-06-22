package com.example.AutoSpotter.controllers;

import java.math.BigDecimal;
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

        // Fetch all vehicle types and pass them to the template
        List<String> vehicleTypes = vehicleRepository.getAllVehicleTypes();
        model.addAttribute("vehicleTypes", vehicleTypes);

        return "add-listing";
    }

    // Back button handler
    @PostMapping("/back")
    public String handleBackButton(@RequestParam("step") int step, HttpSession session) {
        session.setAttribute("step", step);
        return "redirect:/postavi-oglas";
    }

    // Step 1: Vehicle Type form submission
    @PostMapping("/oglas-1")
    public String handleStep1FormSubmission(@RequestParam("listingType") String listingType, HttpSession session) {
        int vehicleTypeId = vehicleRepository.getVehicleTypeIdByListingType(listingType);
        session.setAttribute("vehicleType", listingType);
        session.setAttribute("vehicleTypeId", vehicleTypeId);
        session.setAttribute("step", 2);
        return "redirect:/postavi-oglas";
    }

    // Step 2: Vehicle Details form submission
    @PostMapping("/oglas-2")
    public String handleStep2FormSubmission(@RequestParam("manufacturer") String manufacturer,
                                            @RequestParam("model") String vehicleModel,
                                            @RequestParam("state") String state,
                                            @RequestParam("vehicleTypeId") int vehicleTypeId,
                                            HttpSession session, Model model) {

        // Save the vehicle details to the database
        Vehicle vehicle = new Vehicle(vehicleModel, manufacturer, state, vehicleTypeId);
        int vehicleId = vehicleRepository.saveVehicle(vehicle);
        session.setAttribute("vehicleId", vehicleId);
        session.setAttribute("step", 3);

        // Fetch manufacturers based on the selected vehicle type
        String vehicleType = (String) session.getAttribute("vehicleType");
        List<String> manufacturers = vehicleRepository.getManufacturersByVehicleType(vehicleType);
        model.addAttribute("manufacturers", manufacturers);

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
    public String handleStep4FormSubmission(@RequestParam("title") String title,
                                            @RequestParam("description") String description,
                                            @RequestParam("price") BigDecimal price,
                                            HttpSession session) {

        int vehicleId = (int) session.getAttribute("vehicleId");

        int userId = 1; // Retrieve the userId from the logged-in user

        Listing listing = new Listing();
        listing.setListingTitle(title);
        listing.setListingDescription(description);
        listing.setListingPrice(price);
        listing.setVehicleId(vehicleId);
        listing.setUserId(userId);

        listingRepository.createListing(listing);

        session.invalidate();
        return "redirect:/success";
    }

}
