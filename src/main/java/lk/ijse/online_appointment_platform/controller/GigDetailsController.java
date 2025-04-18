package lk.ijse.online_appointment_platform.controller;


import lk.ijse.online_appointment_platform.dto.AvailabilityDTO;
import lk.ijse.online_appointment_platform.dto.GigDetailsDTO;
import lk.ijse.online_appointment_platform.dto.GigDetailsResponseDTO;
import lk.ijse.online_appointment_platform.dto.UserAppointmentDTO;
import lk.ijse.online_appointment_platform.entity.Appointment;
import lk.ijse.online_appointment_platform.entity.Availability;
import lk.ijse.online_appointment_platform.service.GigService;
import lk.ijse.online_appointment_platform.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("api/v1/gig")
public class GigDetailsController {

    @Autowired
    private GigService gigService;

    private static final String UPLOAD_DIR = "uploads";

    @PostMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveGigDetails(
            @RequestParam("FullName") String fullName,
            @RequestParam("image") MultipartFile image,
            @RequestParam("degree") String degree,
            @RequestParam("experience") String experience,
            @RequestParam("amountCharge") Double amountCharge,
            @RequestParam("location") String location,
            @RequestParam("contactNumber") String contactNumber,
            @RequestParam("maxAppointmentsPerDay") Integer maxAppointmentsPerDay,
            @RequestParam(value = "active", required = false) Boolean active,
            @RequestParam(value = "dateTime", required = false) String dateTime,
            @RequestParam("userId") Integer userId,
            @RequestParam("subCategoryId") Long subCategoryId // Only pass subcategory ID
    ) throws IOException {

        // Default active status if not provided
        if (active == null) {
            active = false;
        }

        // Ensure the dateTime is always valid
        LocalDateTime parsedDateTime = (dateTime == null || dateTime.isEmpty())
                ? LocalDateTime.now()
                : LocalDateTime.parse(dateTime);

        // Ensure the image directory exists
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Handle image upload
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        Files.write(filePath, image.getBytes());

        // Create GigDetailsDTO object
        GigDetailsDTO gigDetailsDTO = new GigDetailsDTO();
        gigDetailsDTO.setFullName(fullName);
        gigDetailsDTO.setImage(filePath.toString().replace("\\", "/"));
        gigDetailsDTO.setDegree(degree);
        gigDetailsDTO.setExperience(experience);
        gigDetailsDTO.setAmountCharge(amountCharge);
        gigDetailsDTO.setLocation(location);
        gigDetailsDTO.setContactNumber(contactNumber);
        gigDetailsDTO.setMaxAppointmentsPerDay(maxAppointmentsPerDay);
        gigDetailsDTO.setActive(active);
        gigDetailsDTO.setDateTime(parsedDateTime);
        gigDetailsDTO.setUserId(userId);
        gigDetailsDTO.setSubCategoryId(subCategoryId); // Pass only subCategoryId

        // Save the gig details using the service
        gigService.addGigDetails(gigDetailsDTO);

        // Return response
        return new ResponseUtil(200, "Gig details saved successfully", gigDetailsDTO);
    }

    @GetMapping("{gigId}/availabilities")
    public List<AvailabilityDTO> getAppointmentsByGigs(@PathVariable Long gigId) {
        return gigService.getAppointmentsByGigId(gigId);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserAppointmentDTO>> getAppointmentsByUser(@PathVariable Long userId) {
        List<UserAppointmentDTO> appointments = gigService.getAppointmentsByUserId(userId);
        return ResponseEntity.ok(appointments);
    }

   /* @GetMapping("/gigs")
    public ResponseEntity<List<GigDetailsResponseDTO>> getAllGigs() {
        List<GigDetailsResponseDTO> gigs = gigService.getAllGigs();
        return ResponseEntity.ok(gigs);
    }*/

 /*   @GetMapping("/gigs/{id}")
    public ResponseEntity<GigDetailsResponseDTO> getGigDetailsById(@PathVariable Long id) {
        return gigService.getGigDetailsByGigId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }*/

    @GetMapping("/gigs/email/{email}")
    public ResponseEntity<GigDetailsResponseDTO> getGigDetailsByEmail(@PathVariable String email) {
        return gigService.getGigDetailsByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



}
