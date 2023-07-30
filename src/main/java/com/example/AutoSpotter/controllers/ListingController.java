package com.example.AutoSpotter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("currentUserId", currentUserId);

        boolean userHasLikedListing = listingRepository.hasUserLikedListing(currentUserId, listingId);
        model.addAttribute("userHasLikedListing", userHasLikedListing);

        return "listing-details";
    }

    @PostMapping("/like/{listingId}")
    public String likeListing(@PathVariable("listingId") int listingId, RedirectAttributes redirectAttributes) {

        int currentUserId = 1;

        listingRepository.likeListing(currentUserId, listingId);
        redirectAttributes.addFlashAttribute("successMessage", "Oglas uspješno spremljen!");

        return "redirect:/oglasi/{listingId}";
    }

    @PostMapping("/dislike/{listingId}")
    public String dislikeListing(@PathVariable("listingId") int listingId, RedirectAttributes redirectAttributes) {

        int currentUserId = 1;

        listingRepository.dislikeListing(currentUserId, listingId);
        redirectAttributes.addFlashAttribute("successMessage", "Oglas obrisan iz vaših spremljenih oglasa!");

        return "redirect:/oglasi/{listingId}";
    }
}