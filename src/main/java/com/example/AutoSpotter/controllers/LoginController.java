package com.example.AutoSpotter.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String processLogin(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        User user = userRepository.findByUsername(username);

        if (user == null) {

            model.addAttribute("errorMessage", "Korisnik s tim korisničkim imenom ne postoji.");
            return "login";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {

            model.addAttribute("errorMessage", "Lozinka je netočna. Pokušajte ponovno!");
            return "login";
        }

        session.setAttribute("loggedInUser", user);

        if (user.getCompanyName() == null) {
            redirectAttributes.addFlashAttribute("successMessage", "Pozdrav " + user.getFirstName() + "!");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Dobrodošli, " + user.getCompanyName() + "!");
        }

        return "redirect:/";
    }
}