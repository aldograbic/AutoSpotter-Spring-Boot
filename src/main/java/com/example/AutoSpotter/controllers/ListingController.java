package com.example.AutoSpotter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.AutoSpotter.classes.listing.Listing;
import com.example.AutoSpotter.classes.listing.ListingRepository;

@Controller
public class ListingController {
    
    private ListingRepository listingRepository;

    public ListingController(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }
    
    @GetMapping("/oglasi/{listingId}")
    public String getListingDetails(@PathVariable("listingId") int listingId, Model model) {
        Listing listing = listingRepository.getListingById(listingId);
        model.addAttribute("listing", listing);
        return "listing-details";
    }
}