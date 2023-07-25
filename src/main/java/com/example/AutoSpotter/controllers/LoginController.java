package com.example.AutoSpotter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String processLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user != null) {
            // fali passwordencoder za hashiranje (treba spring security dependency)
            session.setAttribute("user", user);

            if (user.getCompanyName() == null) {
                redirectAttributes.addFlashAttribute("successMessage", "Pozdrav " + user.getFirstName() + "!");
            } else {
                redirectAttributes.addFlashAttribute("successMessage", "Dobrodošli, " + user.getCompanyName() + "!");
            }

            return "redirect:/";

        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Došlo je do greške prilikom prijave! Provjerite da ste unijeli ispravno korisničko ime i lozinku.");
            
            return "login";
        }
    }
}
