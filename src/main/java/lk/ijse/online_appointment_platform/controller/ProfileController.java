package lk.ijse.online_appointment_platform.controller;

import lk.ijse.online_appointment_platform.dto.ProfileDto;
import lk.ijse.online_appointment_platform.service.ProfileService;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private static final String UPLOAD_DIR = "uploads";

    @Autowired
    private ProfileService profileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseUtil uploadProfileImage(
            @RequestParam("userId") int userId,
            @RequestParam("imageFile") MultipartFile imageFile
    ) throws IOException {

        // Ensure upload directory exists
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Save the image
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        Files.write(filePath, imageFile.getBytes());

        // Create DTO and call service
        ProfileDto profileDto = new ProfileDto(userId, imageFile);
        String savedImagePath = profileService.saveProfile(profileDto, fileName);

        return new ResponseUtil(200, "Profile image saved successfully!", savedImagePath);
    }

}
