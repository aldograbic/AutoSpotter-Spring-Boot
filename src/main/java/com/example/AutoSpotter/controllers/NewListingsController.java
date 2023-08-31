package com.example.AutoSpotter.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.AutoSpotter.classes.listing.JdbcListingRepository;
import com.example.AutoSpotter.classes.listing.Listing;
import com.example.AutoSpotter.classes.location.County;
import com.example.AutoSpotter.classes.location.JdbcLocationRepository;
import com.example.AutoSpotter.classes.user.JdbcUserRepository;
import com.example.AutoSpotter.classes.user.User;
import com.example.AutoSpotter.classes.vehicle.JdbcVehicleRepository;

@Controller
public class NewListingsController {

    private final JdbcListingRepository listingRepository;
    private final JdbcVehicleRepository vehicleRepository;
    private final JdbcLocationRepository locationRepository;
    private final JdbcUserRepository userRepository;

    public NewListingsController(JdbcListingRepository listingRepository, JdbcVehicleRepository vehicleRepository, JdbcLocationRepository locationRepository, JdbcUserRepository userRepository) {
        this.listingRepository = listingRepository;
        this.vehicleRepository = vehicleRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/oglasi")
    public String showNewListings(@RequestParam(required = false) String sort,
                                @RequestParam(required = false) String vehicleType,
                                @RequestParam(required = false) String manufacturer,
                                @RequestParam(required = false) String vehicleModel,
                                @RequestParam(required = false) String bodyType,
                                @RequestParam(required = false) String engineType,
                                @RequestParam(required = false) String transmission,
                                @RequestParam(required = false) String county,
                                @RequestParam(required = false) Integer mileageFrom,
                                @RequestParam(required = false) Integer mileageTo,
                                @RequestParam(required = false) Integer yearFrom,
                                @RequestParam(required = false) Integer yearTo,
                                @RequestParam(required = false) Integer priceFrom,
                                @RequestParam(required = false) Integer priceTo,
                                @RequestParam(required = false) String userType,
                                @RequestParam(defaultValue = "1") int page,
                                Model model) {

        int itemsPerPage = 15;

        List<Listing> newListings;
        List<Listing> allNewListings = listingRepository.getNewListings();

        if (vehicleType != null || manufacturer != null || vehicleModel != null || bodyType != null || engineType != null || transmission != null || county != null || 
            mileageFrom != null || mileageTo != null  || yearFrom != null || yearTo != null || priceFrom != null || priceTo != null || userType != null) {

            newListings = listingRepository.getFilteredListings(vehicleType, manufacturer, vehicleModel, bodyType, engineType, transmission, county, mileageFrom,
                                                                mileageTo, yearFrom, yearTo, priceFrom, priceTo, userType);

        } else {

            newListings = allNewListings;
        }

        int totalItems = newListings.size();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        if (page < 1) {
            page = 1;
        } else if (page > totalPages) {
            page = totalPages;
        }

        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

        if (startIndex >= totalItems) {
            startIndex = Math.max(0, totalItems - 1);
            endIndex = startIndex;
        }

        List<Listing> displayedNewListings = newListings.subList(Math.max(startIndex, 0), Math.max(endIndex, 0));

        int totalListingsCount = newListings.size();

        model.addAttribute("totalListingsCount", totalListingsCount);

        if ("min".equals(sort)) {
            displayedNewListings.sort(Comparator.comparing(Listing::getListingPrice));
        } else if ("max".equals(sort)) {
            displayedNewListings.sort((l1, l2) -> l2.getListingPrice().compareTo(l1.getListingPrice()));
        }
        
        List<String> vehicleTypes = vehicleRepository.getAllVehicleTypes();
        List<String> bodyTypes = vehicleRepository.getAllBodyTypes();
        List<String> engineTypes = vehicleRepository.getAllEngineTypes();
        List<County> counties = locationRepository.getAllCounties();
        List<Integer> years = new ArrayList<>();
        for (int i = 2023; i >= 1900; i--) {
            years.add(i);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        List<String> firstImageUrls = new ArrayList<>();
        for (Listing listing : displayedNewListings) {
            String firstImageUrl = listingRepository.getFirstImageUrlForVehicle(listing.getVehicleId());
            firstImageUrls.add(firstImageUrl);
        }
        model.addAttribute("firstImageUrls", firstImageUrls);

        model.addAttribute("user", user);
        model.addAttribute("years", years);
        model.addAttribute("newListings", displayedNewListings);
        model.addAttribute("vehicleTypes", vehicleTypes);
        model.addAttribute("bodyTypes", bodyTypes);
        model.addAttribute("engineTypes", engineTypes);
        model.addAttribute("counties", counties);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("selectedVehicleType", vehicleType);
        model.addAttribute("selectedManufacturer", manufacturer);
        model.addAttribute("selectedVehicleModel", vehicleModel);
        model.addAttribute("selectedBodyType", bodyType);
        model.addAttribute("selectedEngineType", engineType);
        model.addAttribute("selectedTransmission", transmission);
        model.addAttribute("selectedCounty", county);
        model.addAttribute("selectedMileageFrom", mileageFrom);
        model.addAttribute("selectedMileageTo", mileageTo);
        model.addAttribute("selectedYearFrom", yearFrom);
        model.addAttribute("selectedYearTo", yearTo);
        model.addAttribute("selectedPriceFrom", priceFrom);
        model.addAttribute("selectedPriceTo", priceTo);
        model.addAttribute("selectedUserType", userType);

        if (newListings.isEmpty()) {
            model.addAttribute("noListingsFoundMessage", "Nije pronađen nijedan oglas s odabranim filterima.");
            
            List<Listing> similarListings = listingRepository.getSimilarListingsOfFilteredListings(vehicleType, manufacturer, vehicleModel);
            
            List<String> firstImageUrlsSimilar = new ArrayList<>();
            for (Listing listing : similarListings) {
                String firstImageUrl = listingRepository.getFirstImageUrlForVehicle(listing.getVehicleId());
                firstImageUrlsSimilar.add(firstImageUrl);
            }
            
            int totalItemsSimilar = similarListings.size();
            int totalPagesSimilar = (int) Math.ceil((double) totalItemsSimilar / itemsPerPage);
            totalPagesSimilar = Math.max(totalPagesSimilar, 1);
            int currentPageSimilar = (totalItemsSimilar > 0) ? 1 : 0;
        
            model.addAttribute("totalListingsCount", totalItemsSimilar);
            model.addAttribute("firstImageUrlsSimilar", firstImageUrlsSimilar);
            model.addAttribute("similarListings", similarListings);
            model.addAttribute("currentPage", currentPageSimilar);
            model.addAttribute("totalPages", totalPagesSimilar);
        
            return "new-listings";
        }
        
        return "new-listings";
    }

    @PostMapping("/manufacturers")
    @ResponseBody
    public List<String> getManufacturersByVehicleType(@RequestBody String vehicleType) {
        return vehicleRepository.getManufacturersByVehicleTypeName(vehicleType);
    }

    @PostMapping("/models")
    @ResponseBody
    public List<String> getModelsByManufacturer(@RequestBody String manufacturer) {
        return vehicleRepository.getModelsByManufacturer(manufacturer);
    }
}