package com.example.AutoSpotter.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.AutoSpotter.classes.listing.Listing;
import com.example.AutoSpotter.classes.listing.ListingImage;
import com.example.AutoSpotter.classes.listing.ListingRepository;
import com.example.AutoSpotter.classes.location.County;
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
public class AddListingController {

    private final ListingRepository listingRepository;
    private final VehicleRepository vehicleRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private Storage storage;

    public AddListingController(ListingRepository listingRepository, VehicleRepository vehicleRepository, LocationRepository locationRepository, UserRepository userRepository, Storage storage) {
        this.listingRepository = listingRepository;
        this.vehicleRepository = vehicleRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.storage = storage;
    }

    @GetMapping("/postavi-oglas")
    public String showNewListing(HttpSession session, Model model) {

        Integer step = (Integer) session.getAttribute("step");
        if (step == null) {
            step = 1;
            session.setAttribute("step", step);
        }
        List<Boolean> completedSteps = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            boolean isCompleted = i < step;
            completedSteps.add(isCompleted);
        }
        List<String> vehicleTypes = vehicleRepository.getAllVehicleTypes();
        List<Integer> years = new ArrayList<>();
        for(int i = 2023; i >= 1900; i--) {
            years.add(i);
        }
        List<String> states = vehicleRepository.getAllStates();
        List<County> counties = locationRepository.getAllCounties();
        Map<String, List<String>> citiesByCounty = locationRepository.getCitiesByCounty();
        List<String> engineTypes = vehicleRepository.getAllEngineTypes();
        List<String> transmissionTypes = vehicleRepository.getAllTransmissionTypes();
        List<String> driveTrainTypes = vehicleRepository.getAllDriveTrainTypes();
        List<String> ecoCategories = vehicleRepository.getAllEcoCategories();

        Integer vehicleTypeId = (Integer) session.getAttribute("vehicleTypeId");
        if (vehicleTypeId != null) {
            List<String> manufacturers = vehicleRepository.getManufacturersByVehicleType(vehicleTypeId);
            model.addAttribute("manufacturers", manufacturers);

            if (vehicleTypeId == 1) {
                model.addAttribute("showNumberOfWheelsInput", true);
                model.addAttribute("initialNumberOfWheels", 4);
            } else if (vehicleTypeId == 4) {
                model.addAttribute("showNumberOfWheelsInput", true);
                model.addAttribute("initialNumberOfWheels", 2);
            } else if (vehicleTypeId == 2) {
                model.addAttribute("showNumberOfWheelsInput", true);
                model.addAttribute("initialNumberOfWheels", null);
            } else {
                model.addAttribute("showNumberOfWheelsInput", false);
                model.addAttribute("initialNumberOfWheels", null);
            }

            String engineType = (String) session.getAttribute("engineType");
            model.addAttribute("engineType", engineType);
        }
        List<String> bodyTypes = vehicleRepository.getAllBodyTypes();

        if (step == 2 && 1 == ((Integer) session.getAttribute("vehicleTypeId"))) {
            model.addAttribute("showBodyTypeInput", true);
        } else {
            model.addAttribute("showBodyTypeInput", false);
        }
        if (step == 2 && 2 == ((Integer) session.getAttribute("vehicleTypeId"))) {
            model.addAttribute("showNumberOfWheelsInput", true);
            model.addAttribute("showMaximumAllowableWeightInput", true);
        } else {
            model.addAttribute("showNumberOfWheelsInput", false);
            model.addAttribute("showMaximumAllowableWeightInput", false);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        model.addAttribute("user", user);
        model.addAttribute("step", step);
        model.addAttribute("completedSteps", completedSteps);
        model.addAttribute("vehicleTypes", vehicleTypes);  
        model.addAttribute("years", years);      
        model.addAttribute("states", states);
        model.addAttribute("bodyTypes", bodyTypes);
        model.addAttribute("engineTypes", engineTypes);
        model.addAttribute("ecoCategories", ecoCategories);
        model.addAttribute("transmissionTypes", transmissionTypes);
        model.addAttribute("driveTrainTypes", driveTrainTypes);
        model.addAttribute("counties", counties);
        model.addAttribute("citiesByCounty", citiesByCounty);

        return "add-listing";
    }

    @GetMapping("/back")
    public String handleBackButton(@RequestParam("step") int step, HttpSession session) {
        session.setAttribute("step", step);
        return "redirect:/postavi-oglas";
    }


    @PostMapping("/oglas-1")
    public String handleStep1FormSubmission(@RequestParam("vehicleType") String vehicleType, HttpSession session, Model model) {
        int vehicleTypeId = vehicleRepository.getVehicleTypeId(vehicleType);
        session.setAttribute("vehicleTypeId", vehicleTypeId);
        session.setAttribute("vehicleType", vehicleType);

        session.setAttribute("step", 2);

        return "redirect:/postavi-oglas";
    }

    @PostMapping("/oglas-2")
    public String handleStep2FormSubmission(@RequestParam("manufacturer") String manufacturer,
                                            @RequestParam("model") String vehicleModel,
                                            @RequestParam(value = "bodyType", required = false) String bodyType,
                                            @RequestParam("numberOfDoors") int numberOfDoors,
                                            @RequestParam("numberOfWheels") int numberOfWheels,
                                            @RequestParam(value = "maximumAllowableWeight", required = false) Double maximumAllowableWeight,
                                            @RequestParam("year") int year,
                                            @RequestParam("registeredDate") Date registered,
                                            @RequestParam("color") String color,
                                            @RequestParam("city") String city,
                                            @RequestParam("mileage") int mileage,
                                            @RequestParam("state") String state,
                                            HttpSession session, Model model) {

        int vehicleTypeId = (int) session.getAttribute("vehicleTypeId");
        
        int cityId = locationRepository.getCityIdByName(city);
        List<String> manufacturers = vehicleRepository.getManufacturersByVehicleType(vehicleTypeId);
        session.setAttribute("manufacturers", manufacturers);

        session.setAttribute("manufacturer", manufacturer);
        session.setAttribute("model", vehicleModel);
        session.setAttribute("bodyType", bodyType);
        session.setAttribute("numberOfDoors", numberOfDoors);
        session.setAttribute("numberOfWheels", numberOfWheels);
        session.setAttribute("maximumAllowableWeight", maximumAllowableWeight);
        session.setAttribute("year", year);
        session.setAttribute("registeredDate", registered);
        session.setAttribute("color", color);
        session.setAttribute("cityId", cityId);
        session.setAttribute("city", city);
        session.setAttribute("mileage", mileage);
        session.setAttribute("state", state);

        session.setAttribute("step", 3);
        return "redirect:/postavi-oglas";
    }

    @PostMapping("/oglas-3")
    public String handleStep3FormSubmission(@RequestParam("engineType") String engineType,
                                            @RequestParam("engineDisplacement") double engineDisplacement,
                                            @RequestParam("engineDisplacementCcm3") int engineDisplacementCcm3,
                                            @RequestParam("enginePower") int enginePower,
                                            @RequestParam("fuelConsumption") double fuelConsumption,
                                            @RequestParam("ecoCategory") String ecoCategory,
                                            @RequestParam("transmission") String transmission,
                                            @RequestParam("driveTrain") String driveTrain,
                                            HttpSession session, Model model) {

        session.setAttribute("engineType", engineType);
        session.setAttribute("engineDisplacement", engineDisplacement);
        session.setAttribute("engineDisplacementCcm3", engineDisplacementCcm3);
        session.setAttribute("enginePower", enginePower);
        session.setAttribute("fuelConsumption", fuelConsumption);
        session.setAttribute("ecoCategory", ecoCategory);
        session.setAttribute("transmission", transmission);
        session.setAttribute("driveTrain", driveTrain);

        session.setAttribute("step", 4);
        return "redirect:/postavi-oglas";
    }

    @PostMapping("/oglas-4")
    public String handleStep4FormSubmission(@RequestParam(value = "batteryCapacity", required = false) Double batteryCapacity,
                                            @RequestParam(value = "vehicleRange", required = false) Double vehicleRange,
                                            @RequestParam(value = "chargingTime", required = false) Double chargingTime,
                                            @RequestParam("safetyFeatures") List<String> safetyFeatures,
                                            @RequestParam("extras") List<String> extras,
                                            HttpSession session, Model model) {

        session.setAttribute("batteryCapacity", batteryCapacity);
        session.setAttribute("vehicleRange", vehicleRange);
        session.setAttribute("chargingTime", chargingTime);
        session.setAttribute("safetyFeatures", safetyFeatures);

        int safetyFeaturesId = vehicleRepository.saveVehicleSafetyFeatures(safetyFeatures);
        int extrasId = vehicleRepository.saveVehicleExtras(extras);

        String engineType = (String) session.getAttribute("engineType");

        Double actualBatteryCapacity = batteryCapacity != null ? batteryCapacity : -1.0;
        Double actualVehicleRange = vehicleRange != null ? vehicleRange : -1.0;
        Double actualChargingTime = chargingTime != null ? chargingTime : -1.0;

        String manufacturer = (String) session.getAttribute("manufacturer");
        String vehicleModel = (String) session.getAttribute("model");
        String bodyType = (String) session.getAttribute("bodyType");
        int year = (int) session.getAttribute("year");
        int numberOfDoors = (int) session.getAttribute("numberOfDoors");
        int numberOfWheels = (int) session.getAttribute("numberOfWheels");
        Double maximumAllowableWeight = (Double) session.getAttribute("maximumAllowableWeight");
        double actualMaximumAllowableWeight = maximumAllowableWeight != null ? maximumAllowableWeight : -1.0;
        Date registered = (Date) session.getAttribute("registeredDate");
        String color = (String) session.getAttribute("color");
        int mileage = (int) session.getAttribute("mileage");
        String state = (String) session.getAttribute("state");

        double engineDisplacement = (double) session.getAttribute("engineDisplacement");
        int engineDisplacementCcm3 = (int) session.getAttribute("engineDisplacementCcm3");
        int enginePower = (int) session.getAttribute("enginePower");
        double fuelConsumption = (double) session.getAttribute("fuelConsumption");
        String ecoCategory = (String) session.getAttribute("ecoCategory");
        String transmission = (String) session.getAttribute("transmission");
        String driveTrain = (String) session.getAttribute("driveTrain");

        int cityId = (int) session.getAttribute("cityId");
        int vehicleTypeId = (int) session.getAttribute("vehicleTypeId");

        Vehicle vehicle = new Vehicle(manufacturer, vehicleModel, bodyType, color, registered, mileage, state, year, numberOfWheels, numberOfDoors,
                                    actualMaximumAllowableWeight, engineType, engineDisplacement, engineDisplacementCcm3, enginePower, fuelConsumption, ecoCategory, transmission,
                                    driveTrain, actualBatteryCapacity, actualChargingTime, actualVehicleRange, cityId, vehicleTypeId);

        vehicle.setVehicleSafetyFeaturesId(safetyFeaturesId);
        vehicle.setVehicleExtrasId(extrasId);

        int vehicleId = vehicleRepository.saveVehicle(vehicle);
        session.setAttribute("vehicleId", vehicleId);
        session.setAttribute("selectedSafetyFeatures", safetyFeatures);


        session.setAttribute("step", 5);
        return "redirect:/postavi-oglas";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "Veličina datoteka premašuje maksimalnu dopuštenu veličinu.");
        return "redirect:/postavi-oglas";
    }


    @PostMapping("/oglas-5")
    public String handleStep5FormSubmission(@RequestParam("images") MultipartFile[] images, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            for (MultipartFile image : images) {
                        if (!image.getContentType().startsWith("image/")) {
                            redirectAttributes.addFlashAttribute("errorMessage", "Nepodržani format! Molimo odaberite samo slikovne formate.");
                            return "redirect:/postavi-oglas";
                        }
                    }
        } catch (MaxUploadSizeExceededException e) {
            throw e;
        } catch (Exception e) {
            // Handle other exceptions
        }
        

        int vehicleId = (int) session.getAttribute("vehicleId");

        List<String> imageUrls = saveVehicleImagesToGoogleCloudStorage(images);
        saveVehicleImagesToDatabase(vehicleId, imageUrls);

        session.setAttribute("images", images);
        session.setAttribute("step", 6);
        return "redirect:/postavi-oglas";
    }


    public void uploadImageToGoogleCloudStorage(String bucketName, String objectName, byte[] imageBytes) {
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
        Blob blob = storage.create(blobInfo, imageBytes);
        Acl acl = Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER);
        storage.createAcl(blobId, acl);
    }

    private List<String> saveVehicleImagesToGoogleCloudStorage(MultipartFile[] images) {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile image : images) {
            String bucketName = "autospotterimages";
            String imageName = UUID.randomUUID().toString() + "-" + image.getOriginalFilename();
            byte[] imageBytes;

            try {
                imageBytes = image.getBytes();
                uploadImageToGoogleCloudStorage(bucketName, imageName, imageBytes);
                String imageUrl = "https://storage.googleapis.com/" + bucketName + "/" + imageName;
                imageUrls.add(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return imageUrls;
    }

    private void saveVehicleImagesToDatabase(int vehicleId, List<String> imageUrls) {
        Vehicle vehicle = vehicleRepository.getVehicleById(vehicleId);
    
        if (vehicle != null) {
            for (String imageUrl : imageUrls) {
                ListingImage listingImage = new ListingImage();
                listingImage.setVehicle(vehicle);
                listingImage.setImageUrl(imageUrl);
                listingRepository.saveImageUrlsForVehicle(listingImage);
            }
        } else {
            //prikazat nekak gresku da se kontaktira podrška
        }
    }
    

    @PostMapping("/oglas-6")
    public String handleStep6FormSubmission(@RequestParam("description") String description,
                                            @RequestParam("price") BigDecimal price,
                                            HttpSession session, RedirectAttributes redirectAttributes) {

        int vehicleId = (int) session.getAttribute("vehicleId");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        Listing listing = new Listing();
        listing.setListingDescription(description);
        listing.setListingPrice(price);
        listing.setVehicleId(vehicleId);
        listing.setUserId(user.getId());

        listingRepository.createListing(listing);
        redirectAttributes.addFlashAttribute("successMessage", "Oglas je uspješno dodan!");
        
        session.removeAttribute("step");
        session.removeAttribute("vehicleTypeId");
        session.removeAttribute("vehicleType");
        session.removeAttribute("manufacturer");
        session.removeAttribute("model");
        session.removeAttribute("bodyType");
        session.removeAttribute("numberOfWheels");
        session.removeAttribute("maximumAllowableWeight");
        session.removeAttribute("year");
        session.removeAttribute("registeredDate");
        session.removeAttribute("color");
        session.removeAttribute("cityId");
        session.removeAttribute("city");
        session.removeAttribute("mileage");
        session.removeAttribute("state");
        session.removeAttribute("engineType");
        session.removeAttribute("engineDisplacement");
        session.removeAttribute("enginePower");
        session.removeAttribute("fuelConsumption");
        session.removeAttribute("transmission");
        session.removeAttribute("driveTrain");
        session.removeAttribute("batteryCapacity");
        session.removeAttribute("vehicleRange");
        session.removeAttribute("chargingTime");
        session.removeAttribute("safetyFeatures");
        session.removeAttribute("vehicleId");
        
        return "redirect:/oglasi";
    }
}