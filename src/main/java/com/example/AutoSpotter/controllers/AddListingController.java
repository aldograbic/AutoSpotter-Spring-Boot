package com.example.AutoSpotter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;


@Controller
public class AddListingController {

    @GetMapping("/postavi-oglas")
    public String showLoginForm(HttpSession session, Model model) {
        Integer step = (Integer) session.getAttribute("step");
        if (step == null) {
            step = 1;
            session.setAttribute("step", step);
        }
        model.addAttribute("step", step);
        return "add-listing";
    }

    // Back button handler
    @PostMapping("/back")
    public String handleBackButton(@RequestParam("step") int step, HttpSession session) {
        session.setAttribute("step", step);
        return "redirect:/postavi-oglas";
    }

    // Step 1: Vehicle Type form submission
    @PostMapping("/oglas-1")
    public String handleStep1FormSubmission(@RequestParam("listingType") String listingType, HttpSession session) {
        session.setAttribute("listingType", listingType);
        session.setAttribute("step", 2);
        return "redirect:/postavi-oglas";
    }

    // Step 2: Vehicle Details form submission
    @PostMapping("/oglas-2")
    public String handleStep2FormSubmission(@RequestParam("manufacturer") String manufacturer,
                                            @RequestParam("model") String model,
                                            @RequestParam("state") String state,
                                            HttpSession session) {
        session.setAttribute("manufacturer", manufacturer);
        session.setAttribute("model", model);
        session.setAttribute("state", state);
        session.setAttribute("step", 3);
        return "redirect:/postavi-oglas";
    }

    // Step 3: Vehicle Images form submission
    @PostMapping("/oglas-3")
    public String handleStep3FormSubmission(@RequestParam("images") MultipartFile[] images, HttpSession session) {
        session.setAttribute("images", images);
        session.setAttribute("step", 4);
        return "redirect:/postavi-oglas";
    }

    // Step 4: Listing Title, Description, and Price form submission
    @PostMapping("/oglas-4")
    public String handleStep4FormSubmission(@RequestParam("title") String title,
                                            @RequestParam("description") String description,
                                            @RequestParam("price") double price,
                                            HttpSession session) {
        // Process the submitted data as needed
        // ...

        // Clear the session and redirect to the success page
        session.invalidate();
        return "redirect:/success";
    }
}

