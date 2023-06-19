package com.example.AutoSpotter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    @GetMapping("/kontakt")
    public String showLoginForm() {
        return "contact";
    }
    
    @PostMapping("/kontakt")
    public String processLogin() {
        // Logika za insert u bazu
        // Prikaz poruke hvala na kontaktiranju..
        return "redirect:/";
    }
}
