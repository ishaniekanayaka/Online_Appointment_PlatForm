package lk.ijse.online_appointment_platform.controller;

import lk.ijse.online_appointment_platform.dto.CategoryDTO;
import lk.ijse.online_appointment_platform.entity.Category;
import lk.ijse.online_appointment_platform.service.CategoryService;
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
import java.util.UUID;

@RestController
@CrossOrigin("*")
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

    @PutMapping(value = "update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateCategory(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        CategoryDTO existingCategory = categoryService.getCategoryById(id);
        if (existingCategory == null) {
            throw new RuntimeException("Category not found!");
        }

        // Keep existing image path unless a new image is uploaded
        String imagePath = existingCategory.getImage();

        if (image != null && !image.isEmpty()) {
            // Create upload directory if it doesn't exist
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Delete old image if it exists
            if (imagePath != null) {
                Files.deleteIfExists(Paths.get(imagePath));
            }

            // Generate unique file name
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.write(filePath, image.getBytes());

            imagePath = filePath.toString();
        }

        // Update category details
        CategoryDTO updatedCategory = new CategoryDTO();
        updatedCategory.setId(id);
        updatedCategory.setName(name);
        updatedCategory.setDescription(description);
        updatedCategory.setImage(imagePath);

        categoryService.updateCategory(updatedCategory);
        return new ResponseUtil(200, "Category Updated Successfully", updatedCategory);
    }

    @DeleteMapping("delete/{id}")
    public ResponseUtil deleteCategory(@PathVariable Long id) throws IOException {
        // Retrieve the category details
        CategoryDTO existingCategory = categoryService.getCategoryById(id);
        if (existingCategory == null) {
            throw new RuntimeException("Category not found!");
        }

        // Delete the image file if it exists
        String imagePath = existingCategory.getImage();
        if (imagePath != null) {
            Files.deleteIfExists(Paths.get(imagePath));
        }

        // Delete the category
        categoryService.deleteCategory(id);

        return new ResponseUtil(200, "Category Deleted Successfully", null);
    }

    @GetMapping("getAll")
    public ResponseUtil getAllCategories() {
        return new ResponseUtil(200, "All Categories Retrieved Successfully", categoryService.getAllCategories());
    }

    @GetMapping("names")
    public ResponseUtil getCategoryNames() {
        return new ResponseUtil(200, "Category Names Retrieved Successfully", categoryService.getCategoryNames());
    }

    @GetMapping("name/{name}")
    public ResponseUtil getCategoryByName(@PathVariable String name) {
        return new ResponseUtil(200, "Category Retrieved Successfully", categoryService.getCategoryByName(name));
    }



}
