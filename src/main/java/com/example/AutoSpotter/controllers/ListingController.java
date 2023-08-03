package com.example.AutoSpotter.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.AutoSpotter.classes.contact.EmailService;
import com.example.AutoSpotter.classes.listing.Listing;
import com.example.AutoSpotter.classes.listing.ListingRepository;
import com.example.AutoSpotter.classes.user.User;
import com.example.AutoSpotter.classes.user.UserRepository;

@Controller
public class ListingController {
    
    private ListingRepository listingRepository;
    private UserRepository userRepository;
    private EmailService emailService;

    public ListingController(ListingRepository listingRepository, UserRepository userRepository, EmailService emailService) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }
    
    @GetMapping("/oglasi/{listingId}")
    public String getListingDetails(@PathVariable("listingId") int listingId, Model model) {
        Listing listing = listingRepository.getListingById(listingId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        int currentUserId = userRepository.findByUsername(username).getId();
        boolean userHasLikedListing = listingRepository.hasUserLikedListing(currentUserId, listingId);

        model.addAttribute("user", user);
        model.addAttribute("currentUserId", currentUserId);
        model.addAttribute("listing", listing);
        model.addAttribute("userHasLikedListing", userHasLikedListing);

        return "listing-details";
    }

    @PostMapping("/like/{listingId}")
    public String likeListing(@PathVariable("listingId") int listingId, RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int currentUserId = userRepository.findByUsername(username).getId();

        listingRepository.likeListing(currentUserId, listingId);

        redirectAttributes.addFlashAttribute("successMessage", "Oglas uspješno spremljen!");

        return "redirect:/oglasi/{listingId}";
    }

    @PostMapping("/dislike/{listingId}")
    public String dislikeListing(@PathVariable("listingId") int listingId, RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int currentUserId = userRepository.findByUsername(username).getId();

        listingRepository.dislikeListing(currentUserId, listingId);

        redirectAttributes.addFlashAttribute("successMessage", "Oglas obrisan iz vaših spremljenih oglasa!");

        return "redirect:/oglasi/{listingId}";
    }

    @PostMapping("/oglasi/{listingId}/kontaktiraj")
    public String contactListingUser(@PathVariable("listingId") int listingId,
                                    @RequestParam(value = "firstName", required = false) String firstName,
                                    @RequestParam(value = "lastName", required = false) String lastName,
                                    @RequestParam(value = "companyName", required = false) String companyName,
                                    @RequestParam("email") String email,
                                    @RequestParam("message") String message,
                                    RedirectAttributes redirectAttributes) {

        User listingUser = listingRepository.getListingById(listingId).getUser();
        String listingLink = "http://localhost:8080/oglasi/" + listingId;

        if(firstName == null) {
            emailService.sendContactListingEmail(email, listingUser.getEmail(), "Nova poruka od " + companyName + "! - AutoSpotter", "Poštovani/a " + listingUser.getFirstName() + 
                                                ",\n\nDobili ste novu poruku putem AutoSpotter platforme!" +
                                                "\n\nDetalji poruke:\nNaziv tvrtke: " + companyName + "\nE-mail adresa: " + email + "\nOglas: " + listingLink +
                                                "\nPoruka: " + message + "\n\nMolimo Vas da odgovorite na ovaj e-mail kako biste nastavili s potencijalnim kupcem/prodavateljem oglasa." +
                                                "\n\nHvala što koristite AutoSpotter.\n\nSrdačan pozdrav,\nVaš AutoSpotter tim");
        } else {
            emailService.sendContactListingEmail(email, listingUser.getEmail(), "Nova poruka od " + firstName + ' ' + lastName + "! - AutoSpotter", 
                                                "Poštovani/a " + listingUser.getCompanyName() + ",\n\nDobili ste novu poruku putem AutoSpotter platforme!" +
                                                "\n\nDetalji poruke:\nIme i prezime: " + firstName + ' ' + lastName + "\nE-mail adresa: " + email + "\nOglas: " + listingLink +
                                                "\nPoruka: " + message + "\n\nMolimo Vas da odgovorite na ovaj e-mail kako biste nastavili s potencijalnim kupcem/prodavateljem oglasa." +
                                                "\n\nHvala što koristite AutoSpotter.\n\nSrdačan pozdrav,\nVaš AutoSpotter tim");
        }

        redirectAttributes.addFlashAttribute("successMessage", "Poruka uspješno poslana korisniku " + listingUser.getUsername() + "!");
        return "redirect:/oglasi/{listingId}";
    }
}