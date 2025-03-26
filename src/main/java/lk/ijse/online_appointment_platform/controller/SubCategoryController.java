package lk.ijse.online_appointment_platform.controller;

import lk.ijse.online_appointment_platform.dto.SubCategoryDTO;
import lk.ijse.online_appointment_platform.dto.ResponseDTO;
import lk.ijse.online_appointment_platform.service.SubCategoryService;
import lk.ijse.online_appointment_platform.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/subcategory")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    private static final String UPLOAD_DIR = "upload/";

    @PostMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveSubCategory(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("image") MultipartFile image) throws IOException {

        if (image.isEmpty()) {
            throw new RuntimeException("Image file is required!");
        }

        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        Files.write(filePath, image.getBytes());

        /*String imagePath = "uploads/subcategories/" + fileName;*/

        SubCategoryDTO subCategoryDTO = new SubCategoryDTO();
        subCategoryDTO.setName(name);
        subCategoryDTO.setDescription(description);
        subCategoryDTO.setCategoryId(categoryId);
        subCategoryDTO.setImage(String.valueOf(filePath));

        subCategoryService.addSubCategory(subCategoryDTO);

        return new ResponseUtil(200, "SubCategory Saved", subCategoryDTO);
    }

    @PutMapping(value = "update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateSubCategory(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        SubCategoryDTO existingSubCategory = subCategoryService.getSubCategoryById(id);
        if (existingSubCategory == null) {
            return new ResponseUtil(404, "SubCategory not found!", null);
        }

        String updatedImagePath = existingSubCategory.getImage();

        if (image != null && !image.isEmpty()) {
            // Delete old image
            if (updatedImagePath != null && !updatedImagePath.isEmpty()) {
                Path oldImagePath = Paths.get(updatedImagePath);
                Files.deleteIfExists(oldImagePath);
            }

            // Save new image
            String newFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path newFilePath = Paths.get(UPLOAD_DIR, newFileName);
            Files.write(newFilePath, image.getBytes());

            updatedImagePath = newFilePath.toString();
        }

        existingSubCategory.setName(name);
        existingSubCategory.setDescription(description);
        existingSubCategory.setCategoryId(categoryId);
        existingSubCategory.setImage(updatedImagePath);

        subCategoryService.updateSubCategory(existingSubCategory);

        return new ResponseUtil(200, "SubCategory Updated Successfully", existingSubCategory);
    }

}
