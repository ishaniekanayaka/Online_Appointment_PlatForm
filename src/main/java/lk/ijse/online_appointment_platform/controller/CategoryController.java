package lk.ijse.online_appointment_platform.controller;

import lk.ijse.online_appointment_platform.dto.CategoryDTO;
import lk.ijse.online_appointment_platform.dto.ResponseDTO;
import lk.ijse.online_appointment_platform.dto.UserDTO;
import lk.ijse.online_appointment_platform.entity.Category;
import lk.ijse.online_appointment_platform.service.CategoryService;
import lk.ijse.online_appointment_platform.util.ResponseUtil;
import lk.ijse.online_appointment_platform.util.VarList;
import lk.ijse.online_appointment_platform.util.picEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController

@RequestMapping("api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveCategory(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image) throws IOException {

        if (image.isEmpty()) {
            throw new RuntimeException("Image file is required!");
        }
        // Create upload directory if not exists
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Generate unique file name
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        System.out.println(image.getOriginalFilename());
        // Define file path
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.write(filePath, image.getBytes());

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(name);
        categoryDTO.setDescription(description);
        categoryDTO.setImage(String.valueOf(filePath));

        categoryService.addCategory(categoryDTO);

        return new ResponseUtil(200, "Category Saved", categoryDTO);
    }

    @PutMapping(value = "update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateCategory(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        CategoryDTO existingCategory = categoryService.getCategoryById(id);
        if (existingCategory == null) {
            return new ResponseUtil(404, "Category not found!", null);
        }

        String updatedImagePath = existingCategory.getImage();

        if (image != null && !image.isEmpty()) {
            // Delete the old image correctly
            if (updatedImagePath != null && !updatedImagePath.isEmpty()) {
                Path oldImagePath = Paths.get(updatedImagePath); // Correcting path issue
                Files.deleteIfExists(oldImagePath);
            }

            // Save the new image
            String newFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path newFilePath = Paths.get(UPLOAD_DIR, newFileName); // Ensure correct path handling
            Files.write(newFilePath, image.getBytes());
            updatedImagePath = "uploads/" + newFileName;
        }

        existingCategory.setName(name);
        existingCategory.setDescription(description);
        existingCategory.setImage(updatedImagePath);

        categoryService.updateCategory(existingCategory);

        return new ResponseUtil(200, "Category Updated Successfully", existingCategory);
    }



    @DeleteMapping("delete/{id}")
    public ResponseUtil deleteCategory(@PathVariable Long id) throws IOException {
        CategoryDTO existingCategory = categoryService.getCategoryById(id);
        if (existingCategory == null) {
            return new ResponseUtil(404, "Category not found!", null);
        }

        // Delete image file if it exists
        String imagePath = existingCategory.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            Path filePath = Paths.get(imagePath); // Ensure correct path handling
            Files.deleteIfExists(filePath);
        }

        categoryService.deleteCategory(id);

        return new ResponseUtil(200, "Category Deleted Successfully", null);
    }



    @GetMapping("getAll")
    public ResponseEntity<ResponseDTO> getAllCategories() {
        try {
            List<CategoryDTO> categoryList = categoryService.getAllCategories();

            // Fix image path formatting
            for (CategoryDTO category : categoryList) {
                if (category.getImage() != null) {
                    category.setImage(category.getImage().replace("\\", "/")); // Replace backslashes with forward slashes
                }
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.Created, "Success", categoryList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }


    @GetMapping("names")
    public ResponseUtil getCategoryNames() {
        return new ResponseUtil(200, "Category Names Retrieved Successfully", categoryService.getCategoryNames());
    }

    @GetMapping("name/{name}")
    public ResponseUtil getCategoryByName(@PathVariable String name) {
        CategoryDTO category = categoryService.getCategoryByName(name);
        if (category != null && category.getImage() != null) {
            category.setImage(category.getImage().replace("\\", "/"));
        }
        return new ResponseUtil(200, "Category Retrieved Successfully", category);
    }

    @GetMapping("{id}")
    public ResponseUtil getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        if (category != null && category.getImage() != null) {
            category.setImage(category.getImage().replace("\\", "/")); // Fix path format
        }
        return new ResponseUtil(200, "Category Retrieved Successfully", category);
    }



}