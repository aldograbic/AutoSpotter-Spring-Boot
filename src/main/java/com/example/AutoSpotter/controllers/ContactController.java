package com.example.AutoSpotter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.AutoSpotter.classes.contact.Contact;
import com.example.AutoSpotter.classes.contact.ContactRepository;

@Controller
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping("/kontakt")
    public String showContactForm() {
        return "contact";
    }

    @PostMapping("/kontakt")
    public String processContactForm(@ModelAttribute Contact contact) {
        
        contactRepository.saveContact(contact);
        // Prikaz poruke hvala na kontaktiranju..
        return "redirect:/";
    }
}