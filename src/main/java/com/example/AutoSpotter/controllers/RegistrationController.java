package com.example.AutoSpotter.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.AutoSpotter.classes.contact.EmailService;
import com.example.AutoSpotter.classes.location.CityConverter;
import com.example.AutoSpotter.classes.location.LocationRepository;
import com.example.AutoSpotter.classes.user.User;
import com.example.AutoSpotter.classes.user.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final CityConverter cityConverter;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;


    @Autowired
    public RegistrationController(UserRepository userRepository, LocationRepository locationRepository, CityConverter cityConverter, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.cityConverter = cityConverter;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @GetMapping("/registracija")
    public String showRegistrationForm(HttpSession session, Model model) {

        Integer step = (Integer) session.getAttribute("step");
        if (step == null) {
            step = 1;
            session.setAttribute("step", step);
        }
        List<Boolean> completedSteps = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            boolean isCompleted = i < step;
            completedSteps.add(isCompleted);
        }

        Map<String, List<String>> citiesByCounty = locationRepository.getCitiesByCounty();
        model.addAttribute("citiesByCounty", citiesByCounty);
        model.addAttribute("step", step);
        model.addAttribute("completedSteps", completedSteps);
        String userType = (String) session.getAttribute("userType");
        model.addAttribute("userType", userType);
        
        return "registration";
    }

    @GetMapping("/backRegistration")
    public String handleBackButton(@RequestParam("step") int step, HttpSession session) {
        session.setAttribute("step", step);
        return "redirect:/registracija";
    }

    @PostMapping("/registracija-1")
    public String handleStep1FormSubmission(@RequestParam("userType") String userType, HttpSession session, Model model) {
        
        session.setAttribute("userType", userType);

        session.setAttribute("step", 2);

        return "redirect:/registracija";
    }

    @PostMapping("/registracija-2")
    public String handleStep2FormSubmission(@RequestParam(value ="firstName", required = false) String firstName,
                                            @RequestParam(value ="lastName", required = false) String lastName,
                                            @RequestParam(value ="companyName", required = false) String companyName,
                                            @RequestParam(value ="companyOIB", required = false) String companyOIB,
                                            @RequestParam("address") String address,
                                            @RequestParam("city") String city,
                                            HttpSession session,
                                            Model model) {
        
        int cityId = locationRepository.getCityIdByName(city);
        session.setAttribute("firstName", firstName);
        session.setAttribute("lastName", lastName);
        session.setAttribute("companyName", companyName);
        session.setAttribute("companyOIB", companyOIB);
        session.setAttribute("address", address);
        session.setAttribute("city", city);
        session.setAttribute("cityId", cityId);

        session.setAttribute("step", 3);

        return "redirect:/registracija";
    }

    @PostMapping("/registracija-3")
    public String handleStep3FormSubmission(@RequestParam("email") String email,
                                            @RequestParam("phoneNumber") String phoneNumber,
                                            @RequestParam("username") String username,
                                            @RequestParam("password") String password,
                                            @RequestParam("confirmPassword") String confirmPassword,
                                            @RequestParam(value = "acceptedTermsOfService", required = false, defaultValue = "false") boolean acceptedTermsOfService,
                                            HttpSession session,
                                            RedirectAttributes redirectAttributes,
                                            Model model) {

        String firstName = (String) session.getAttribute("firstName");
        String lastName = (String) session.getAttribute("lastName");
        String companyName = (String) session.getAttribute("companyName");
        String companyOIB = (String) session.getAttribute("companyOIB");
        String address = (String) session.getAttribute("address");
        int cityId = (int) session.getAttribute("cityId");


        User user = new User(username, password, firstName, lastName, companyName, companyOIB, address, phoneNumber, email, cityId);

        User existingUserEmail = userRepository.findByEmail(user.getEmail());
        if (existingUserEmail != null) {

            model.addAttribute("errorMessage", "Već postoji korisnik s istom e-mail adresom!");
            
            return "redirect:/registracija";
        }
        User existingUserUsername = userRepository.findByUsername(user.getUsername());
        if (existingUserUsername != null) {

            model.addAttribute("errorMessage", "Već postoji korisnik s istim korisničkim imenom!");
            
            return "redirect:/registracija";
        }
        if (!acceptedTermsOfService) {
            model.addAttribute("errorMessage", "Morate prihvatiti uvjete korištenja usluge da bi se registrirali!");
            
            return "redirect:/registracija";
        }

        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Potvrda lozinke se ne podudara s unesenom lozinkom!");
            
            return "redirect:/registracija";
        }
        
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        String token = UUID.randomUUID().toString();
        user.setConfirmationToken(token);
        user.setEmailVerified(false);

        model.addAttribute("user", user);

        userRepository.save(user);

        String confirmationLink = "http://localhost:8080/potvrdi?token=" + token;
        emailService.sendVerificationEmail(user.getEmail(), confirmationLink);

        session.invalidate();
        redirectAttributes.addFlashAttribute("successMessage", "Poslali smo vam e-mail s uputama za potvrdu e-mail adrese!");

        return "redirect:/";
    }

    @GetMapping("/potvrdi")
    public String confirmEmail(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByConfirmationToken(token);
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Neispravan token za potvrdu e-mail adrese.");
            return "redirect:/";
        }

        if (user.isEmailVerified()) {
            redirectAttributes.addFlashAttribute("infoMessage", "E-mail adresa je već potvrđena.");
            return "redirect:/";
        }

        user.setEmailVerified(true);
        userRepository.updateEmailVerification(user);

        redirectAttributes.addFlashAttribute("successMessage", "Uspješno ste potvrdili e-mail adresu!");

        return "redirect:/";
    }
}