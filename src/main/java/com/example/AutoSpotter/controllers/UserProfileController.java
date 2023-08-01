package com.example.AutoSpotter.controllers;

import java.util.List;

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
public class UserProfileController {

    private UserRepository userRepository;
    private ListingRepository listingRepository;

    public UserProfileController(UserRepository userRepository, ListingRepository listingRepository) {
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
    }

    @GetMapping("/korisnicki-profil")
    public String showCurrentUserProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);
        int listingCount = listingRepository.getListingsCountByUserId(user.getId());
        int likedListingsCount = listingRepository.getLikedListingsCountByUserId(user.getId());
        List<Listing> userListing = listingRepository.getListingsByUserId(user.getId());
        List<Listing> likedListings = listingRepository.getListingsLikedByUser(user.getId());

        model.addAttribute("authenticatedUserId", user.getId());
        model.addAttribute("user", user);
        model.addAttribute("listingCount", listingCount);
        model.addAttribute("likedListingsCount", likedListingsCount);
        model.addAttribute("userListing", userListing);
        model.addAttribute("likedListings", likedListings);
        return "user-profile";
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

    @PostMapping("/listing/{listingId}/delete")
    public String deleteListing(@PathVariable("listingId") int listingId, RedirectAttributes redirectAttributes) {
        listingRepository.deleteListing(listingId);

        redirectAttributes.addFlashAttribute("successMessage", "Oglas uspješno obrisan!");
        return "redirect:/korisnicki-profil";
    }

    @PostMapping("/user/{userId}/delete")
    public String deleteUserProfile(@PathVariable("userId") int userId, RedirectAttributes redirectAttributes) {
        
        userRepository.deleteUser(userId);
        
        redirectAttributes.addFlashAttribute("successMessage", "Profil je uspješno obrisan. Zahvaljujemo na korištenju naše usluge!");
        return "redirect:/";
    }
}