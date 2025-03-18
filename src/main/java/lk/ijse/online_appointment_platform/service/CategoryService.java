package lk.ijse.online_appointment_platform.service;

import lk.ijse.online_appointment_platform.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryDTO categoryDTO);
    void updateCategory(CategoryDTO categoryDTO);
    void deleteCategory(String id);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(String id);
}
