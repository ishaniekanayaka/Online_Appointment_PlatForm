package lk.ijse.online_appointment_platform.controller;


import lk.ijse.online_appointment_platform.dto.GigDetailsDTO;
import lk.ijse.online_appointment_platform.service.GigService;
import lk.ijse.online_appointment_platform.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/gig")
public class GigDetailsController {

    @Autowired
    private GigService gigService;

    private static final String UPLOAD_DIR = "/uploads";

    @PostMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveGigDetails(
            @RequestParam("FullName") String FullName,
            @RequestParam("image") MultipartFile image,
            @RequestParam("degree") String degree,
            @RequestParam("experience") String experience,
            @RequestParam("amountCharge") Double amountCharge,
            @RequestParam("location") String location,
            @RequestParam("contactNumber") String contactNumber,
            @RequestParam("maxAppointmentsPerDay") Integer maxAppointmentsPerDay,
            @RequestParam(value = "active", required = false) Boolean active, // Optional, defaults to false
            @RequestParam(value = "date", required = false) String date, // Optional, defaults to current date
            @RequestParam(value = "userId") Integer userId,
            @RequestParam(value = "categoryId") Long categoryId
    ) throws IOException {

        // Set default values if not provided
        if (active == null) {
            active = false; // Default to false if not provided
        }

        // Ensure the date is always valid
        if (date == null || date.isEmpty()) {
            date = LocalDate.now().toString(); // Default to current date
        }


        // Ensure the image directory exists
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Handle the uploaded image file
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        Files.write(filePath, image.getBytes());

        // Create and populate the GigDetailsDTO entity
        GigDetailsDTO gigDetailsDTO = new GigDetailsDTO();
        gigDetailsDTO.setFullName(FullName);
        gigDetailsDTO.setImage(filePath.toString().replace("\\", "/")); // Replace backslashes with forward slashes for paths
        gigDetailsDTO.setDegree(degree);
        gigDetailsDTO.setExperience(experience);
        gigDetailsDTO.setAmountCharge(amountCharge);
        gigDetailsDTO.setLocation(location);
        gigDetailsDTO.setContactNumber(contactNumber);
        gigDetailsDTO.setMaxAppointmentsPerDay(maxAppointmentsPerDay);
        gigDetailsDTO.setActive(active);
        gigDetailsDTO.setDate(date);
        gigDetailsDTO.setUserId(userId);
        gigDetailsDTO.setCategoryId(categoryId);

        // Save the gig details using the service
        gigService.addGigDetails(gigDetailsDTO);

        // Return a response indicating success
        return new ResponseUtil(200, "Gig details saved successfully", gigDetailsDTO);
    }
}
