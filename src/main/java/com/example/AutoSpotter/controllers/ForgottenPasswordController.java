package com.example.AutoSpotter.controllers;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.AutoSpotter.classes.user.User;
import com.example.AutoSpotter.classes.user.UserRepository;

// import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class ForgottenPasswordController {

    private final JavaMailSender mailSender;
    private final Map<String, String> passwordResetTokens;
    private final UserRepository userRepository;
    // private final PasswordEncoder passwordEncoder;

    public ForgottenPasswordController(JavaMailSender mailSender, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.passwordResetTokens = new HashMap<>();
        this.userRepository = userRepository;
        // this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/zaboravljena-lozinka")
    public String showForgottenPasswordForm() {
        return "forgotten-password";
    }

    @PostMapping("/zaboravljena-lozinka")
    public String processForgottenPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        String token = UUID.randomUUID().toString();

        passwordResetTokens.put(token, email);

        String resetLink = "http://localhost:8080/reset-lozinke?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Zaboravljena lozinka");
        message.setText("Kliknite na link ispod kako biste resetirali svoju lozinku:\n" + resetLink);
        mailSender.send(message);

        redirectAttributes.addFlashAttribute("successMessage", "Poslali smo vam e-mail s uputama za obnovu lozinke!");

        return "redirect:/";
    }

    @GetMapping("/reset-lozinke")
    public String showResetPasswordForm(@RequestParam("token") String token) {
        String email = passwordResetTokens.get(token);
        if (email != null) {
            return "reset-password";
        } else {
            return "reset-password-error";
        }
    }

    @PostMapping("/reset-lozinke")
    public String resetPassword(@RequestParam("token") String token, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        // Validate the token
        String email = passwordResetTokens.get(token);
        if (email != null) {
            // Token is valid, update the user's password
            User user = userRepository.findByEmail(email);
            if (user != null) {
                // user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);

                passwordResetTokens.remove(token);

                redirectAttributes.addFlashAttribute("successMessage", "Lozinka uspješno promijenjena! Sada se možete prijaviti s novom lozinkom.");

                return "redirect:/login";
            }
        }
        return "reset-password-error";
    }
}