package com.example.AutoSpotter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddListingController {

    @GetMapping("/postavi-oglas")
    public String showLoginForm() {
        return "add-listing";
    }
    
}
