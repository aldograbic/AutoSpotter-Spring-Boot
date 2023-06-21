package com.example.AutoSpotter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewListingsController {

    @GetMapping("/novi-oglasi")
    public String showLoginForm() {
        return "new-listings";
    }
}
