package com.example.AutoSpotter.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.AutoSpotter.classes.listing.Listing;
import com.example.AutoSpotter.classes.listing.ListingRepository;
import com.example.AutoSpotter.classes.user.User;
import com.example.AutoSpotter.classes.user.UserRepository;

@Controller
public class UserProfileController {

    private UserRepository userRepository;
    private ListingRepository listingRepository;

    public UserProfileController(UserRepository userRepository, ListingRepository listingRepository) {
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
    }

    @GetMapping("/korisnicki-profil/{userId}")
    public String showUserProfile(@PathVariable("userId") int userId, Model model) {
        User user = userRepository.getUserById(userId);
        int listingCount = listingRepository.getListingsCountByUserId(userId);
        List<Listing> userListing = listingRepository.getListingsByUserId(userId);


        model.addAttribute("user", user);
        model.addAttribute("listingCount", listingCount);
        model.addAttribute("userListing", userListing);
        return "user-profile";
    }
}