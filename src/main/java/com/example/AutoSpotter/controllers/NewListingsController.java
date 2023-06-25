package com.example.AutoSpotter.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.AutoSpotter.classes.listing.JdbcListingRepository;
import com.example.AutoSpotter.classes.listing.Listing;

@Controller
public class NewListingsController {

    private final JdbcListingRepository listingRepository;

    public NewListingsController(JdbcListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    @GetMapping("/novi-oglasi")
    public String showNewListings(Model model) {
        List<Listing> newListings = listingRepository.getNewListings();
        model.addAttribute("newListings", newListings);
        return "new-listings";
    }
}