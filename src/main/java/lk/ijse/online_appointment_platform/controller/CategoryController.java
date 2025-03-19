package lk.ijse.online_appointment_platform.controller;

import lk.ijse.online_appointment_platform.dto.CategoryDTO;
import lk.ijse.online_appointment_platform.service.CategoryService;
import lk.ijse.online_appointment_platform.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.addCategory(categoryDTO);
        return new ResponseUtil(200, "Category Saved", null);
    }

    @PutMapping(value = "update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.updateCategory(categoryDTO);
        return new ResponseUtil(200, "Category Update", null);
    }

    @DeleteMapping(path = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil deleteCategory(@PathVariable("id") String id){
        categoryService.deleteCategory(id);
        return new ResponseUtil(200, "Category Delete", null);
    }

    @GetMapping(value = "getAll")
    public ResponseUtil getAllCategory(){
        return new ResponseUtil(200,
                "Customer List",
                categoryService.getAllCategories()
                );
    }

    @GetMapping("getCategoryById/{id}")
    public ResponseUtil getCategoryById(@PathVariable String id){
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        return new ResponseUtil(200,"Category Found", categoryDTO);
    }

    @GetMapping("getCategoryName")
    public ResponseUtil getCategoryNames(){
        return new ResponseUtil(
        200,
        "Category name list",
        categoryService.getCategoryNames());
    }

    @GetMapping("getCategoryByName/{name}")
    public ResponseUtil getCategoryByName(@PathVariable String name){
        CategoryDTO categoryDTO = categoryService.getCategoryByName(name);
        return new ResponseUtil(200, "category Found", categoryDTO);
    }

}
