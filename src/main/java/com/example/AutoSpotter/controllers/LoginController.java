package com.example.AutoSpotter.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @GetMapping("/prijava")
    public String showLoginForm(RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {

            redirectAttributes.addFlashAttribute("infoMessage", "Već ste prijavljeni! Ako želite pristupiti stranici prvo se odjavite.");
            return "redirect:/";
        }

        return "login";
    }
}