package com.example.AutoSpotter.classes.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final ExecutorService executorService;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        this.executorService = Executors.newFixedThreadPool(10); // You can adjust the pool size as needed
    }

    public void sendVerificationEmail(String to, String confirmationLink) {
        execute(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Potvrdi e-mail adresu - AutoSpotter");
            message.setText("Odaberite poveznicu ispod kako biste potvrdili svoju e-mail adresu:\n" + confirmationLink);
            mailSender.send(message);
        });
    }

    public void sendForgottenPasswordEmail(String to, String resetLink) {
        execute(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Zaboravljena lozinka - AutoSpotter");
            message.setText("Odaberite poveznicu ispod kako biste poništili svoju lozinku:\n" + resetLink);
            mailSender.send(message);
        });
    }

    public void sendContactListingEmail(String from, String to, String subject, String messageBody) {
        execute(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setReplyTo(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(messageBody);
            mailSender.send(message);
        });
    }

    private void execute(Runnable task) {
        executorService.execute(task);
    }
}
