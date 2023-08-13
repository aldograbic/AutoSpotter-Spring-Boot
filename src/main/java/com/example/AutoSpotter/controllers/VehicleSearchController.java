package com.example.AutoSpotter.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.AutoSpotter.classes.location.LocationRepository;
import com.example.AutoSpotter.classes.user.User;
import com.example.AutoSpotter.classes.user.UserRepository;
import com.example.AutoSpotter.classes.vehicle.VehicleRepository;

@Controller
public class VehicleSearchController {

    private final VehicleRepository vehicleRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    public VehicleSearchController(UserRepository userRepository, VehicleRepository vehicleRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.locationRepository = locationRepository;
    }

    @GetMapping("/pretraga")
    public String showVehicleSearchForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        List<String> vehicleTypes = vehicleRepository.getAllVehicleTypes();


        List<String> bodyTypes = vehicleRepository.getAllBodyTypes();
        List<String> states = vehicleRepository.getAllStates();
        List<Integer> years = new ArrayList<>();
        for(int i = 2023; i >= 1900; i--) {
            years.add(i);
        }
        Map<String, List<String>> citiesByCounty = locationRepository.getCitiesByCounty();
        List<String> engineTypes = vehicleRepository.getAllEngineTypes();
        List<String> transmissionTypes = vehicleRepository.getAllTransmissionTypes();
        List<String> driveTrainTypes = vehicleRepository.getAllDriveTrainTypes();
        List<String> ecoCategories = vehicleRepository.getAllEcoCategories();


        model.addAttribute("vehicleTypes", vehicleTypes);
        model.addAttribute("bodyTypes", bodyTypes);
        model.addAttribute("states", states);
        model.addAttribute("years", years);
        model.addAttribute("citiesByCounty", citiesByCounty);
        model.addAttribute("engineTypes", engineTypes);
        model.addAttribute("transmissionTypes", transmissionTypes);
        model.addAttribute("driveTrainTypes", driveTrainTypes);
        model.addAttribute("ecoCategories", ecoCategories);

        model.addAttribute("user", user);
        return "vehicle-search";
    }

    @PostMapping("/manufacturersSearch")
    @ResponseBody
    public List<String> getManufacturersByVehicleType(@RequestBody String vehicleType) {
        return vehicleRepository.getManufacturersByVehicleTypeName(vehicleType);
    }

    @PostMapping("/modelsSearch")
    @ResponseBody
    public List<String> getModelsByManufacturer(@RequestBody String manufacturer) {
        return vehicleRepository.getModelsByManufacturer(manufacturer);
    }
}