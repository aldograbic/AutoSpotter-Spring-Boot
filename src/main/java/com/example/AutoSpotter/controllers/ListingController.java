package com.example.AutoSpotter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

        int currentUserId = 1;
        // int currentUserId = userService.getCurrentlyLoggedInUserId();
        model.addAttribute("currentUserId", currentUserId);

        boolean userHasLikedListing = listingRepository.hasUserLikedListing(currentUserId, listingId);
        model.addAttribute("userHasLikedListing", userHasLikedListing);

        return "listing-details";
    }

    @PostMapping("/oglasi/{listingId}/like")
    public String likeListing(@PathVariable("listingId") int listingId) {

        int currentUserId = 1;
        // int currentUserId = userService.getCurrentlyLoggedInUserId();

        listingRepository.likeListing(currentUserId, listingId);

        return "redirect:/oglasi/{listingId}";
    }

    @PostMapping("/oglasi/{listingId}/dislike")
    public String dislikeListing(@PathVariable("listingId") int listingId) {
        int currentUserId = 1;
        // int currentUserId = userService.getCurrentlyLoggedInUserId();

        listingRepository.dislikeListing(currentUserId, listingId);

        return "redirect:/oglasi/{listingId}";
    }
}