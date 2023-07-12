package com.example.AutoSpotter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

    @GetMapping("/kontakt")
    public String showContactForm() {
        return "contact";
    }

    @PostMapping("/kontakt")
    public String processContactForm(@RequestParam("name") String name,
                                     @RequestParam("email") String email,
                                     @RequestParam("message") String message) {
        // Logika za insert u bazu
        // Prikaz poruke hvala na kontaktiranju..
        return "redirect:/";
    }
}
