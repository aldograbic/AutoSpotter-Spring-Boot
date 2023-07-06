package com.example.AutoSpotter.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.AutoSpotter.classes.listing.JdbcListingRepository;
import com.example.AutoSpotter.classes.listing.Listing;
import com.example.AutoSpotter.classes.vehicle.JdbcVehicleRepository;

@Controller
public class NewListingsController {

    private final JdbcListingRepository listingRepository;
    private final JdbcVehicleRepository vehicleRepository;

    public NewListingsController(JdbcListingRepository listingRepository, JdbcVehicleRepository vehicleRepository) {
        this.listingRepository = listingRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("/oglasi")
    public String showNewListings(Model model) {
        List<Listing> newListings = listingRepository.getNewListings();
        List<String> vehicleTypes = vehicleRepository.getAllVehicleTypes();
        List<String> bodyTypes = vehicleRepository.getAllBodyTypes();
        List<String> engineTypes = vehicleRepository.getAllEngineTypes();
        List<String> counties = vehicleRepository.getAllCounties();
        List<Integer> years = new ArrayList<>();
        for(int i = 2023; i >= 1900; i--) {
            years.add(i);
        }

        model.addAttribute("years", years);
        model.addAttribute("newListings", newListings);
        model.addAttribute("vehicleTypes", vehicleTypes);
        model.addAttribute("bodyTypes", bodyTypes);
        model.addAttribute("engineTypes", engineTypes);
        model.addAttribute("counties", counties);

        return "new-listings";
    }

    @PostMapping("/manufacturers")
    @ResponseBody
    public List<String> getManufacturersByVehicleType(@RequestBody String vehicleType) {
        return listingRepository.getManufacturersByVehicleType(vehicleType);
    }

    @PostMapping("/models")
    @ResponseBody
    public List<String> getModelsByManufacturer(@RequestBody String manufacturer) {
        return listingRepository.getModelsByManufacturer(manufacturer);
    }
}