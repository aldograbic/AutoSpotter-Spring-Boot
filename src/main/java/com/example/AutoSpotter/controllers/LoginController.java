package com.example.AutoSpotter.controllers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.AutoSpotter.classes.user.CustomUserDetailsService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;

    public LoginController(CustomUserDetailsService customUserDetailsService, AuthenticationManager authenticationManager) {
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/prijava")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/prijava")
    public String processLogin(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if (userDetails != null) {

            String rawPassword = password;

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, rawPassword, userDetails.getAuthorities());

            Authentication authenticatedUser = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
            session.setAttribute("loggedInUser", authenticatedUser.getPrincipal());

        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password.");
            return "redirect:/prijava";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Pozdrav " + username + "!");
        return "redirect:/";
    }
}