package com.example.AutoSpotter.controllers;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.AutoSpotter.classes.contact.Contact;
import com.example.AutoSpotter.classes.contact.ContactRepository;

import org.springframework.mail.javamail.JavaMailSender;

@Controller
public class ContactController {

    private final ContactRepository contactRepository;
    private final JavaMailSender javaMailSender;

    public ContactController(ContactRepository contactRepository, JavaMailSender javaMailSender) {
        this.contactRepository = contactRepository;
        this.javaMailSender = javaMailSender;
    }

    @GetMapping("/kontakt")
    public String showContactForm() {
        return "contact";
    }

    @PostMapping("/kontakt")
    public String processContactForm(@ModelAttribute Contact contact) {

        contactRepository.saveContact(contact);
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("autospotter.contact@gmail.com");
        message.setTo("autospotter.contact@gmail.com");
        message.setSubject("Nova kontakt poruka!");
        message.setText("Detalji poruke:\n\n" +
                "Ime: " + contact.getName() + "\n" +
                "Email: " + contact.getEmail() + "\n" +
                "Poruka: " + contact.getMessage());

        javaMailSender.send(message);
        
        return "redirect:/";
    }
}