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

import java.util.Optional;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ModelMapper modelMapper;



    public void addSubCategory(SubCategoryDTO subCategoryDTO) {
        Optional<Category> categoryOptional = categoryRepository.findById(subCategoryDTO.getCategoryId());
        if (categoryOptional.isPresent()) {
            SubCategory subCategory = new SubCategory();
            subCategory.setName(subCategoryDTO.getName());
            subCategory.setDescription(subCategoryDTO.getDescription());
            subCategory.setImage(subCategoryDTO.getImage());
            subCategory.setCategory(categoryOptional.get()); // Linking with parent category

            subCategoryRepository.save(subCategory);
        } else {
            throw new RuntimeException("Category not found for ID: " + subCategoryDTO.getCategoryId());
        }
    }


    @Override
    public SubCategoryDTO getSubCategoryById(Long id) {
        Optional<SubCategory> subCategoryOptional = subCategoryRepository.findById(id);
        if (subCategoryOptional.isPresent()) {
            return modelMapper.map(subCategoryOptional.get(), SubCategoryDTO.class);
        } else {
            throw new RuntimeException("SubCategory not found for ID: " + id);
        }
    }

    @Override
    public void updateSubCategory(SubCategoryDTO subCategoryDTO) {
        Optional<SubCategory> existingSubCategoryOptional = subCategoryRepository.findById(subCategoryDTO.getId());

        if (existingSubCategoryOptional.isPresent()) {
            SubCategory existingSubCategory = existingSubCategoryOptional.get();
            existingSubCategory.setName(subCategoryDTO.getName());
            existingSubCategory.setDescription(subCategoryDTO.getDescription());
            existingSubCategory.setImage(subCategoryDTO.getImage());

            Optional<Category> categoryOptional = categoryRepository.findById(subCategoryDTO.getCategoryId());
            categoryOptional.ifPresent(existingSubCategory::setCategory);

            subCategoryRepository.save(existingSubCategory);
        } else {
            throw new RuntimeException("SubCategory not found for ID: " + subCategoryDTO.getId());
        }
    }

}
