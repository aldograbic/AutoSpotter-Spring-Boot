package com.example.AutoSpotter.controllers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.AutoSpotter.classes.user.CustomUserDetailsService;
import com.example.AutoSpotter.classes.user.User;
import com.example.AutoSpotter.classes.user.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public LoginController(CustomUserDetailsService customUserDetailsService, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @GetMapping("/prijava")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/odjava")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        redirectAttributes.addFlashAttribute("successMessage", "Uspješna odjava. Vidimo se uskoro!");
        return "redirect:/";
    }

    @PostMapping("/prijava")
    public String processLogin(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        User user = userRepository.findByUsername(username);

        if (userDetails != null) {

            if (!user.isEmailVerified()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Prije prijave morate potvrditi svoju e-mail adresu!");
                return "redirect:/prijava";
            }

            String rawPassword = password;

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, rawPassword, userDetails.getAuthorities());

            Authentication authenticatedUser = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

            UserDetails authenticatedUserDetails = (UserDetails) authenticatedUser.getPrincipal();


            session.setAttribute("loggedInUser", authenticatedUserDetails.getUsername());
            session.setAttribute("userFirstName", user.getFirstName());
            session.setAttribute("userLastName", user.getLastName()); //koji kurac vise (ima na chatgpt alternativa)

        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password.");
            return "redirect:/prijava";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Pozdrav " + username + "!");
        return "redirect:/";
    }
}