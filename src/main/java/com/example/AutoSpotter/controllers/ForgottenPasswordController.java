package com.example.AutoSpotter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ForgottenPasswordController {

    @GetMapping("/zaboravljena-lozinka")
    public String showForgottenPasswordForm() {
        return "forgotten-password";
    }

    @PostMapping("/zaboravljena-lozinka")
    public String processForgottenPassword() {
        // Logika za slanje maila za reset sifre

        // Prikazati poruku na toj stranici da se provjeri mail
        return "redirect:/";
    }
    
}
