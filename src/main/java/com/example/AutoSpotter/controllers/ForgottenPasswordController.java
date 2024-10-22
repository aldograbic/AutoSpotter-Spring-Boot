package com.example.AutoSpotter.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.AutoSpotter.classes.contact.EmailService;
import com.example.AutoSpotter.classes.user.User;
import com.example.AutoSpotter.classes.user.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class ForgottenPasswordController {

    private final EmailService emailService;
    private final Map<String, String> passwordResetTokens;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ForgottenPasswordController(EmailService emailService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.emailService = emailService;
        this.passwordResetTokens = new HashMap<>();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/zaboravljena-lozinka")
    public String showForgottenPasswordForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        model.addAttribute("user", user);

        return "forgotten-password";
    }

    @PostMapping("/zaboravljena-lozinka")
    public String processForgottenPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ne postoji korisnički račun s upisanom e-mail adresom!");

            return "redirect:/zaboravljena-lozinka";

        }
        String token = UUID.randomUUID().toString();
        passwordResetTokens.put(token, email);
        String resetLink = "http://localhost:8080/reset-lozinke?token=" + token;

        emailService.sendForgottenPasswordEmail(email, resetLink);

        redirectAttributes.addFlashAttribute("successMessage", "Poslali smo vam e-mail s uputama za obnovu lozinke!");

        return "redirect:/";
    }

    @GetMapping("/reset-lozinke")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model, RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        model.addAttribute("user", user);

        String email = passwordResetTokens.get(token);

        if (email != null) {
            model.addAttribute("token", token);
            return "reset-password";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Dogodila se greška pri dohvaćanju tokena!");
            return "redirect:/reset-lozinke?token=" + token;
        }
    }


    @PostMapping("/reset-lozinke")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("password") String password,
                                @RequestParam("confirmPassword") String confirmPassword,
                                RedirectAttributes redirectAttributes) {

        String email = passwordResetTokens.get(token);

        if (email != null) {
            User user = userRepository.findByEmail(email);

            if (user != null) {
                if (!password.equals(confirmPassword)) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Potvrda lozinke se ne podudara s unesenom lozinkom!");
                    
                    return "redirect:/reset-lozinke?token=" + token;
                }

                user.setPassword(passwordEncoder.encode(password));
                userRepository.updatePassword(user);

                passwordResetTokens.remove(token);

                redirectAttributes.addFlashAttribute("successMessage", "Lozinka uspješno promijenjena! Sada se možete prijaviti s novom lozinkom.");

                return "redirect:/prijava";
            }
        }

        redirectAttributes.addFlashAttribute("errorMessage", "Dogodila se greška pri resetiranju lozinke!");
        return "redirect:/reset-lozinke?token=" + token;
    }
}