package com.example.AutoSpotter.controllers;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgottenPasswordController {

    private final JavaMailSender mailSender;

    public ForgottenPasswordController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping("/zaboravljena-lozinka")
    public String showForgottenPasswordForm() {
        return "forgotten-password";
    }

    @PostMapping("/zaboravljena-lozinka")
    public String processForgottenPassword(@RequestParam("email") String email) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Zaboravljena lozinka");
        message.setText("Kliknite na link ispod kako bi resetirali svoju lozinku.");
        mailSender.send(message);

        // obavijest da je otislo na mail
        return "redirect:/";
    }
    
}
