package com.example.AutoSpotter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @GetMapping("/registracija")
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping("/registracija")
    public String processRegistration() {
        // Process the registration form submission
        // You can add your registration logic here, such as validating the credentials
        return "redirect:/login";
    }
}
