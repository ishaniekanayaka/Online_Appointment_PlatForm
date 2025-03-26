package lk.ijse.online_appointment_platform.service;

import lk.ijse.online_appointment_platform.dto.CategoryDTO;
import lk.ijse.online_appointment_platform.dto.SubCategoryDTO;

import java.util.List;

public interface SubCategoryService {
    void addSubCategory(SubCategoryDTO subCategoryDTO);
    public SubCategoryDTO getSubCategoryById(Long id);
    public void updateSubCategory(SubCategoryDTO subCategoryDTO);
    public List<SubCategoryDTO> getAllSubCategories();
}
