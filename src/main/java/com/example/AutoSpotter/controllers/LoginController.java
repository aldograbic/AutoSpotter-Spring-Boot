package com.example.AutoSpotter.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @GetMapping("/prijava")
    public String showLoginForm(RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
        redirectAttributes.addFlashAttribute("infoMessage", "Već ste prijavljeni! Ako želite pristupiti stranici prvo se odjavite.");
        return "redirect:/";
    }

        return "login";
    }

    @PostMapping("/odjava")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/?uspjesnaOdjava";
    }

    // @PostMapping("/prijava")
    // public String processLogin(@RequestParam("username") String username,
    //                         @RequestParam("password") String password,
    //                         Model model, HttpSession session, RedirectAttributes redirectAttributes) {

    //     UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
    //     User user = userRepository.findByUsername(username);

    //     if (userDetails != null) {

    //         if (!user.isEmailVerified()) {
    //             return "redirect:/prijava?greskaPotvrda";
    //         }

    //         String rawPassword = password;
            
    //         Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, rawPassword, userDetails.getAuthorities());

    //         authentication = authenticationManager.authenticate(authentication);
    //         SecurityContextHolder.getContext().setAuthentication(authentication);

    //         UserDetails authenticatedUserDetails = (UserDetails) authentication.getPrincipal();
            
    //         session.setAttribute("loggedInUser", authenticatedUserDetails.getUsername());

    //     } else {

    //         return "redirect:/prijava?greska";
    //     }

    //     return "redirect:/?uspjesnaPrijava";
    // }
}