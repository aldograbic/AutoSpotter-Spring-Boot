package com.example.AutoSpotter.controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.AutoSpotter.classes.contact.Contact;
import com.example.AutoSpotter.classes.contact.ContactRepository;
import com.example.AutoSpotter.classes.user.User;
import com.example.AutoSpotter.classes.user.UserRepository;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class ContactController {

    private final ContactRepository contactRepository;
    private final JavaMailSender javaMailSender;
    private final ExecutorService executorService;
    private final UserRepository userRepository;

    public ContactController(ContactRepository contactRepository, JavaMailSender javaMailSender, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.javaMailSender = javaMailSender;
        this.executorService = Executors.newFixedThreadPool(10);
        this.userRepository = userRepository;
    }

    @GetMapping("/kontakt")
    public String showContactForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        model.addAttribute("user", user);
        return "contact";
    }

    @PostMapping("/kontakt")
    public String processContactForm(@ModelAttribute Contact contact, RedirectAttributes redirectAttributes) {
        contactRepository.saveContact(contact);

        executorService.execute(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("autospotter.contact@gmail.com");
            message.setTo("autospotter.contact@gmail.com");
            message.setSubject("Nova kontakt poruka!");
            message.setText("Detalji poruke:\n\n" +
                    "Ime: " + contact.getName() + "\n" +
                    "Email: " + contact.getEmail() + "\n" +
                    "Poruka: " + contact.getMessage());

            javaMailSender.send(message);
        });

        redirectAttributes.addFlashAttribute("successMessage", "Hvala vam što ste nas kontaktirali! Vaša poruka je uspješno poslana.");

        return "redirect:/";
    }

}