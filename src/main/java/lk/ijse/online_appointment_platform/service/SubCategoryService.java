package lk.ijse.online_appointment_platform.service;

import lk.ijse.online_appointment_platform.dto.CategoryDTO;
import lk.ijse.online_appointment_platform.dto.SubCategoryDTO;

import java.util.List;

public interface SubCategoryService {
    void addSubCategory(SubCategoryDTO subCategoryDTO);
    SubCategoryDTO getSubCategoryById(Long id);
    void updateSubCategory(SubCategoryDTO subCategoryDTO);
    List<SubCategoryDTO> getAllSubCategories();
    void deleteSubCategory(Long id);

}
