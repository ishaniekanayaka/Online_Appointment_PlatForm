package lk.ijse.online_appointment_platform.service.impl;

import lk.ijse.online_appointment_platform.dto.CategoryDTO;
import lk.ijse.online_appointment_platform.dto.SubCategoryDTO;
import lk.ijse.online_appointment_platform.entity.Category;
import lk.ijse.online_appointment_platform.entity.SubCategory;
import lk.ijse.online_appointment_platform.repo.CategoryRepository;
import lk.ijse.online_appointment_platform.repo.SubCategoryRepository;
import lk.ijse.online_appointment_platform.service.SubCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addSubCategory(SubCategoryDTO subCategoryDTO) {
        // Create a new SubCategory entity
        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryDTO.getId());
        subCategory.setName(subCategoryDTO.getName());
        subCategory.setDescription(subCategoryDTO.getDescription());
        subCategory.setImage(subCategoryDTO.getImage());

        // Fetch the Category entity using the category NAME
        Category category = categoryRepository.findById(subCategoryDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + subCategoryDTO.getCategoryId()));


        // Set the Category entity in the SubCategory entity
        subCategory.setCategory(category);

        // Save the SubCategory entity
        subCategoryRepository.save(subCategory);
    }

}
