package com.example.AutoSpotter.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.AutoSpotter.classes.location.CityConverter;
import com.example.AutoSpotter.classes.location.LocationRepository;
import com.example.AutoSpotter.classes.user.User;
import com.example.AutoSpotter.classes.user.UserRepository;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final CityConverter cityConverter;
    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public RegistrationController(UserRepository userRepository, LocationRepository locationRepository, CityConverter cityConverter, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.cityConverter = cityConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registracija")
    public String showRegistrationForm(Model model) {
        Map<String, List<String>> citiesByCounty = locationRepository.getCitiesByCounty();
        model.addAttribute("citiesByCounty", citiesByCounty);
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registracija")
    public String processRegistration(@ModelAttribute("user") User user, @RequestParam("city") String cityName, Model model, RedirectAttributes redirectAttributes) {

        User existingUserEmail = userRepository.findByEmail(user.getEmail());
        if (existingUserEmail != null) {

            model.addAttribute("errorMessage", "Već postoji korisnik s istom e-mail adresom!");
            Map<String, List<String>> citiesByCounty = locationRepository.getCitiesByCounty();
            model.addAttribute("citiesByCounty", citiesByCounty);
            return "registration";
        }

        User existingUserUsername = userRepository.findByUsername(user.getUsername());
        if (existingUserUsername != null) {

            model.addAttribute("errorMessage", "Već postoji korisnik s istim korisničkim imenom!");
            Map<String, List<String>> citiesByCounty = locationRepository.getCitiesByCounty();
            model.addAttribute("citiesByCounty", citiesByCounty);
            return "registration";
        }

        int cityId = locationRepository.getCityIdByName(cityName);
        user.setCityId(cityId);

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        model.addAttribute("user", user);

        userRepository.save(user);

        redirectAttributes.addFlashAttribute("successMessage", "Poslali smo vam e-mail s uputama za potvrdu e-mail adrese!");

        return "redirect:/";
    }
}