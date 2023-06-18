package com.example.AutoSpotter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VehicleSearch {

    @GetMapping("/pretraga")
    public String showLoginForm() {
        return "vehicle-search";
    }
    
}
