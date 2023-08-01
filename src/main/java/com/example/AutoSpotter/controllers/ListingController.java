package com.example.AutoSpotter.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.AutoSpotter.classes.listing.Listing;
import com.example.AutoSpotter.classes.listing.ListingRepository;
import com.example.AutoSpotter.classes.user.User;
import com.example.AutoSpotter.classes.user.UserRepository;

@Controller
public class ListingController {
    
    private ListingRepository listingRepository;
    private UserRepository userRepository;

    public ListingController(ListingRepository listingRepository, UserRepository userRepository) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }
    
    @GetMapping("/oglasi/{listingId}")
    public String getListingDetails(@PathVariable("listingId") int listingId, Model model) {
        Listing listing = listingRepository.getListingById(listingId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        int currentUserId = userRepository.findByUsername(username).getId();
        boolean userHasLikedListing = listingRepository.hasUserLikedListing(currentUserId, listingId);

        model.addAttribute("user", user);
        model.addAttribute("currentUserId", currentUserId);
        model.addAttribute("listing", listing);
        model.addAttribute("userHasLikedListing", userHasLikedListing);

        return "listing-details";
    }

    @PostMapping("/like/{listingId}")
    public String likeListing(@PathVariable("listingId") int listingId, RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int currentUserId = userRepository.findByUsername(username).getId();

        listingRepository.likeListing(currentUserId, listingId);

        redirectAttributes.addFlashAttribute("successMessage", "Oglas uspješno spremljen!");

        return "redirect:/oglasi/{listingId}";
    }

    @PostMapping("/dislike/{listingId}")
    public String dislikeListing(@PathVariable("listingId") int listingId, RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int currentUserId = userRepository.findByUsername(username).getId();

        listingRepository.dislikeListing(currentUserId, listingId);

        redirectAttributes.addFlashAttribute("successMessage", "Oglas obrisan iz vaših spremljenih oglasa!");

        return "redirect:/oglasi/{listingId}";
    }
}