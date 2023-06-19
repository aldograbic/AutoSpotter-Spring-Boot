package com.example.AutoSpotter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/prijava")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/prijava")
    public String processLogin() {
        // Process the login form submission
        // You can add your login logic here, such as validating the credentials
        return "redirect:/";
    }
}
