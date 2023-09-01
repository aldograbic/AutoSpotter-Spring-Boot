package com.example.AutoSpotter.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.AutoSpotter.classes.user.User;
import com.example.AutoSpotter.classes.user.UserRepository;

@Controller
public class LegalController {

    private final UserRepository userRepository;

    public LegalController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/uvjeti-koristenja")
    public String showTermsOfService(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        model.addAttribute("user", user);
        return "terms-of-service";
    }

    @GetMapping("/pravila-privatnosti")
    public String showPrivacyPolicy(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        model.addAttribute("user", user);
        return "privacy-policy";
    }
}