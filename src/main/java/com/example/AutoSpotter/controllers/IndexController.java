package com.example.AutoSpotter.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.AutoSpotter.classes.listing.Listing;
import com.example.AutoSpotter.classes.listing.ListingRepository;
import com.example.AutoSpotter.classes.user.User;
import com.example.AutoSpotter.classes.user.UserRepository;
import com.example.AutoSpotter.config.CustomOAuth2User;

@Controller
public class IndexController {

    private final UserRepository userRepository;
    private final ListingRepository listingRepository;

    public IndexController(UserRepository userRepository, ListingRepository listingRepository) {
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
    }

    @GetMapping("/")
    public String showIndex(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // String username;
        // if (authentication.getPrincipal() instanceof CustomOAuth2User) {
        //     CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        //     username = oAuth2User.getAttribute("name");
        // } else {
        //     username = authentication.getName();
        // }

        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        List<Listing> newestListings = listingRepository.getNewestListings();

        List<String> firstImageUrls = new ArrayList<>();
        for (Listing listing : newestListings) {
            String firstImageUrl = listingRepository.getFirstImageUrlForVehicle(listing.getVehicleId());
            firstImageUrls.add(firstImageUrl);
        }

        model.addAttribute("firstImageUrls", firstImageUrls);
        model.addAttribute("user", user);
        model.addAttribute("newestListings", newestListings);
        return "index";
    }
}