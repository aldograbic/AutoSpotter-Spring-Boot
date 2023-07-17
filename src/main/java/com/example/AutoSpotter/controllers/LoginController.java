package com.example.AutoSpotter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.AutoSpotter.classes.user.User;
import com.example.AutoSpotter.classes.user.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/prijava")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/prijava")
    public String processLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user != null) {
            // fali passwordencoder za hashiranje (treba spring security dependency)
            session.setAttribute("user", user);
            return "redirect:/";
        } else {
            // Pogrešno korisničko ime ili lozinka, prikaži poruku o grešci
            return "login";
        }
    }
}
