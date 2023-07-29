package com.example.AutoSpotter.classes.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to, String confirmationLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Potvrdi e-mail adresu - AutoSpotter");
        message.setText("Odaberite poveznicu ispod kako biste potvrdili svoju e-mail adresu:\n" + confirmationLink);
        mailSender.send(message);
    }

    public void sendForgottenPasswordEmail(String to, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Zaboravljena lozinka - AutoSpotter");
        message.setText("Odaberite poveznicu ispod kako biste poništili svoju lozinku:\n" + resetLink);
        mailSender.send(message);
    }
}