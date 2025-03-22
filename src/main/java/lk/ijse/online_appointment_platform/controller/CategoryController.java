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


    @PutMapping(value = "update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            categoryService.updateCategory(categoryDTO);
            return new ResponseUtil(200, "Category Updated Successfully!", categoryDTO);
        } catch (RuntimeException e) {
            return new ResponseUtil(400, e.getMessage(), null);
        }
    }

    @DeleteMapping(value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteCategory(@PathVariable("id") Long id) {
        try {
            categoryService.deleteCategory(id);
            return new ResponseUtil(200, "Category Deleted Successfully!", null);
        } catch (RuntimeException e) {
            return new ResponseUtil(400, e.getMessage(), null);
        }
    }

    @GetMapping(value = "getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllCategories() {
        return new ResponseUtil(200, "Category List Retrieved Successfully!", categoryService.getAllCategories());
    }

    @GetMapping(value = "getCategoryById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getCategoryById(@PathVariable Long id) {
        try {
            CategoryDTO categoryDTO = categoryService.getCategoryById(id);
            return new ResponseUtil(200, "Category Found!", categoryDTO);
        } catch (RuntimeException e) {
            return new ResponseUtil(404, "Category Not Found: " + e.getMessage(), null);
        }
    }

    @GetMapping(value = "getCategoryName", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getCategoryNames() {
        return new ResponseUtil(200, "Category Names Retrieved Successfully!", categoryService.getCategoryNames());
    }

    @GetMapping(value = "getCategoryByName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getCategoryByName(@PathVariable String name) {
        try {
            CategoryDTO categoryDTO = categoryService.getCategoryByName(name);
            return new ResponseUtil(200, "Category Found!", categoryDTO);
        } catch (RuntimeException e) {
            return new ResponseUtil(404, "Category Not Found: " + e.getMessage(), null);
        }
    }
    
}
