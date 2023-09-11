package com.example.AutoSpotter.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.AutoSpotter.classes.contact.EmailService;
import com.example.AutoSpotter.classes.listing.Listing;
import com.example.AutoSpotter.classes.listing.ListingRepository;
import com.example.AutoSpotter.classes.location.LocationRepository;
import com.example.AutoSpotter.classes.user.User;
import com.example.AutoSpotter.classes.user.UserRepository;
import com.example.AutoSpotter.classes.vehicle.Vehicle;
import com.example.AutoSpotter.classes.vehicle.VehicleRepository;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserProfileController {

    private UserRepository userRepository;
    private ListingRepository listingRepository;
    private LocationRepository locationRepository;
    private EmailService emailService;
    private Storage storage;
    private VehicleRepository vehicleRepository;

    public UserProfileController(UserRepository userRepository, ListingRepository listingRepository, LocationRepository locationRepository, EmailService emailService, Storage storage, VehicleRepository vehicleRepository) {
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
        this.locationRepository = locationRepository;
        this.emailService = emailService;
        this.storage = storage;
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("/korisnicki-profil")
    public String showCurrentUserProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        
        int listingCount = listingRepository.getListingsCountByUserId(user.getId());
        int likedListingsCount = listingRepository.getLikedListingsCountByUserId(user.getId());
        List<Listing> userListing = listingRepository.getListingsByUserId(user.getId());
        List<Listing> likedListings = listingRepository.getListingsLikedByUser(user.getId());

        List<String> firstImageUrls = new ArrayList<>();
        for (Listing listing : userListing) {
            String firstImageUrl = listingRepository.getFirstImageUrlForVehicle(listing.getVehicleId());
            firstImageUrls.add(firstImageUrl);
        }
        model.addAttribute("firstImageUrls", firstImageUrls);

        model.addAttribute("authenticatedUserId", user.getId());
        model.addAttribute("user", user);
        model.addAttribute("listingCount", listingCount);
        model.addAttribute("likedListingsCount", likedListingsCount);
        model.addAttribute("userListing", userListing);
        model.addAttribute("likedListings", likedListings);
        return "user-profile";
    }

    @GetMapping("/korisnicki-profil/osobni-podaci")
    public String showUserProfileDetails(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        Map<String, List<String>> citiesByCounty = locationRepository.getCitiesByCounty();
        model.addAttribute("citiesByCounty", citiesByCounty);

        model.addAttribute("authenticatedUserId", user.getId());
        model.addAttribute("user", user);
        return "user-details";
    }

    @PostMapping("korisnik/{userId}/promijeni-sliku")
    public String updateUserProfileImage(@PathVariable("userId") int userId, @RequestParam("profileImage") MultipartFile profileImage) {
        User user = userRepository.getUserById(userId);

        if (!profileImage.isEmpty()) {
            String bucketName = "autospotterimages";
            String imageName = UUID.randomUUID().toString() + "-" + profileImage.getOriginalFilename();
            byte[] imageBytes;
    
            try {
                imageBytes = profileImage.getBytes();
                uploadImageToGoogleCloudStorage(bucketName, imageName, imageBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
    
            String profileImageUrl = "https://storage.googleapis.com/" + bucketName + "/" + imageName;
            user.setProfileImage(profileImageUrl);
            userRepository.updateUser(user);
        }
    
        return "redirect:/korisnicki-profil";

    }

    public void uploadImageToGoogleCloudStorage(String bucketName, String objectName, byte[] imageBytes) {
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
        Blob blob = storage.create(blobInfo, imageBytes);
        Acl acl = Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER);
        storage.createAcl(blobId, acl);
    }

    @PostMapping("/korisnik/{userId}/promijeni-podatke")
    public String updateUserDetails(@PathVariable("userId") int userId, User updatedUser, RedirectAttributes redirectAttributes, HttpSession session) {
        User user = userRepository.getUserById(userId);
    
        if (!user.getUsername().equals(updatedUser.getUsername())) {

            User existingUserWithNewUsername = userRepository.findByUsername(updatedUser.getUsername());
            if (existingUserWithNewUsername != null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Već postoji korisnik s istim korisničkim imenom!");
                return "redirect:/korisnicki-profil/osobni-podaci";
            }

            user.setUsername(updatedUser.getUsername());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setAddress(updatedUser.getAddress());
            user.setCityId(updatedUser.getCity().getId());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setEmail(updatedUser.getEmail());
            userRepository.updateUser(user);
            session.invalidate();
            redirectAttributes.addFlashAttribute("successMessage", "Podaci su uspješno ažurirani! Molimo da se ponovno prijavite za nastavak.");
            return "redirect:/prijava";
        }

        if (!user.getEmail().equals(updatedUser.getEmail())) {

            User existingUserWithNewEmail = userRepository.findByEmail(updatedUser.getEmail());
            if (existingUserWithNewEmail != null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Već postoji korisnik s istom e-mail adresom!");
                return "redirect:/korisnicki-profil/osobni-podaci";
            }

            String token = UUID.randomUUID().toString();
            user.setConfirmationToken(token);
            user.setEmailVerified(false);
            
            user.setUsername(updatedUser.getUsername());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setAddress(updatedUser.getAddress());
            user.setCityId(updatedUser.getCity().getId());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setEmail(updatedUser.getEmail());
    
            userRepository.updateUser(user);
            String confirmationLink = "http://localhost:8080/potvrdi?token=" + token;
            emailService.sendVerificationEmail(user.getEmail(), confirmationLink);
            session.invalidate();
    
            redirectAttributes.addFlashAttribute("successMessage", "Podaci su uspješno ažurirani! Prije prijave, provjerite svoj e-mail za potvrdu nove e-mail adrese.");
            return "redirect:/";
        }
    
        user.setUsername(updatedUser.getUsername());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setAddress(updatedUser.getAddress());
        user.setCityId(updatedUser.getCity().getId());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setEmail(updatedUser.getEmail());
    
        userRepository.updateUser(user);
    
        redirectAttributes.addFlashAttribute("successMessage", "Podaci su uspješno ažurirani!");
    
        return "redirect:/korisnicki-profil";
    }

    @GetMapping("/korisnicki-profil/{userId}/{userUsername}")
    public String showUserProfile(@PathVariable("userId") int userId, @PathVariable("userUsername") String userUsername, Model model) {
        User user = userRepository.getUserById(userId);
        int listingCount = listingRepository.getListingsCountByUserId(userId);
        List<Listing> userListing = listingRepository.getListingsByUserId(userId);

        List<String> firstImageUrls = new ArrayList<>();
        for (Listing listing : userListing) {
            String firstImageUrl = listingRepository.getFirstImageUrlForVehicle(listing.getVehicleId());
            firstImageUrls.add(firstImageUrl);
        }
        model.addAttribute("firstImageUrls", firstImageUrls);

        model.addAttribute("user", user);
        model.addAttribute("listingCount", listingCount);
        model.addAttribute("userListing", userListing);
        return "user-profile";
    }


    @GetMapping("/oglas/{listingId}/uredi")
        public String showEditListing(@PathVariable("listingId") int listingId, Model model){
        Listing listing = listingRepository.getListingById(listingId);
        int vehicleId = listing.getVehicleId();
        List<String> imageUrls = listingRepository.getImageUrlsForVehicle(vehicleId);
        List<String> states = vehicleRepository.getAllStates();
        Map<String, List<String>> citiesByCounty = locationRepository.getCitiesByCounty();

        model.addAttribute("imageUrls", imageUrls);
        model.addAttribute("listing", listing);
        model.addAttribute("states", states);
        model.addAttribute("citiesByCounty", citiesByCounty);
            
        return "edit-listing";
        }


    @PostMapping("/oglas/{listingId}/uredi")
    public String editListing(@PathVariable("listingId") int listingId, 
                              @RequestParam("listingDescription") String listingDescription,
                              @RequestParam("mileage") int mileage,
                              @RequestParam("state") String state,
                              @RequestParam("city") String city,
                              RedirectAttributes redirectAttributes,
                              @RequestParam("priceInput") BigDecimal priceInput) {
        Listing listing = listingRepository.getListingById(listingId);
        Vehicle vehicle = vehicleRepository.getVehicleById(listing.getVehicleId());
        
        
        listing.setListingDescription(listingDescription);
        listing.setListingPrice(priceInput);
        listing.getVehicle().setMileage(mileage);
        listing.getVehicle().setState(state);
        
        
        listingRepository.editListing(listing);
        

        redirectAttributes.addFlashAttribute("successMessage", "Oglas je uspješno uređen.");
        return "redirect:/korisnicki-profil";
    }

    @PostMapping("/listing/{listingId}/delete")
    public String deleteListing(@PathVariable("listingId") int listingId, RedirectAttributes redirectAttributes) {
        listingRepository.deleteListing(listingId);

        redirectAttributes.addFlashAttribute("successMessage", "Oglas uspješno obrisan!");
        return "redirect:/korisnicki-profil";
    }

    @PostMapping("/user/{userId}/delete")
    public String deleteUserProfile(@PathVariable("userId") int userId, RedirectAttributes redirectAttributes) {
        
        userRepository.deleteUser(userId);
        
        redirectAttributes.addFlashAttribute("successMessage", "Profil je uspješno obrisan. Zahvaljujemo na korištenju naše usluge!");
        return "redirect:/";
    }
}