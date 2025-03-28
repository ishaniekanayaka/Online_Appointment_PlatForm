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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/subcategory")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    private static final String UPLOAD_DIR = "uploads/";

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
        //subCategoryDTO.setImage(String.valueOf(filePath));
        subCategoryDTO.setImage(filePath.toString().replace("\\", "/"));

        subCategoryService.addSubCategory(subCategoryDTO);

        return new ResponseUtil(200, "SubCategory Saved", subCategoryDTO);
    }

    @PutMapping(value = "update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateSubCategory(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        SubCategoryDTO existingSubCategory = subCategoryService.getSubCategoryById(id);

        if (existingSubCategory == null) {
            return new ResponseUtil(404, "SubCategory Not Found", null);
        }

        String imagePath = existingSubCategory.getImage();

        if (image != null && !image.isEmpty()) {
            // Save the new image
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.write(filePath, image.getBytes());

            imagePath = filePath.toString().replace("\\", "/");
        }

        existingSubCategory.setName(name);
        existingSubCategory.setDescription(description);
        existingSubCategory.setImage(imagePath);

        subCategoryService.updateSubCategory(existingSubCategory);

        return new ResponseUtil(200, "SubCategory Updated Successfully", existingSubCategory);
    }


    @GetMapping(value = "getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllSubCategories() {
        List<SubCategoryDTO> subCategoryDTOList = subCategoryService.getAllSubCategories();
        return new ResponseUtil(200, "SubCategories fetched successfully", subCategoryDTOList);
    }

    @DeleteMapping(value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteSubCategory(@PathVariable Long id) {
        SubCategoryDTO subCategoryDTO = subCategoryService.getSubCategoryById(id);
        if (subCategoryDTO == null) {
            return new ResponseUtil(404, "SubCategory not found!", null);
        }

        // Delete the image if exists
        if (subCategoryDTO.getImage() != null && !subCategoryDTO.getImage().isEmpty()) {
            Path imagePath = Paths.get(subCategoryDTO.getImage());
            try {
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                return new ResponseUtil(500, "Failed to delete image", null);
            }
        }

        subCategoryService.deleteSubCategory(id);

        return new ResponseUtil(200, "SubCategory Deleted Successfully", null);
    }

}