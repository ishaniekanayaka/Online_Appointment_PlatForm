package lk.ijse.online_appointment_platform.controller;


import lk.ijse.online_appointment_platform.dto.SubCategoryDTO;

import lk.ijse.online_appointment_platform.service.SubCategoryService;
import lk.ijse.online_appointment_platform.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:63342",allowedHeaders = "*")
@RequestMapping("api/v1/subCategory")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subcategoryService;

    @PostMapping("save")
    public ResponseUtil saveSubCategory(@RequestBody SubCategoryDTO subCategoryDTO){
        subcategoryService.addSubCategory(subCategoryDTO);
        return new ResponseUtil(201,"Category Saved", null);
    }

  /*  @GetMapping(value = "getAll")
    public ResponseUtil getAllSubCategory(){
        return new ResponseUtil(
                200,
                "Customer List",
                subcategoryService.getAllSubCategories()
        );
    }*/

}

