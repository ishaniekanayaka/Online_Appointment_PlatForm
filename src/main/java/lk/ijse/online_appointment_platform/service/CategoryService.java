package lk.ijse.online_appointment_platform.service;

import lk.ijse.online_appointment_platform.dto.CategoryDTO;
import lk.ijse.online_appointment_platform.entity.SubCategory;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryDTO categoryDTO);
    void updateCategory(CategoryDTO categoryDTO);
    void deleteCategory(Long id);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
    List<String>getCategoryNames();
    CategoryDTO getCategoryByName(String name);
    public List<SubCategory> getSubCategoriesByCategoryId(Long categoryId);
  //  public List<SubCategory> getSubCategoriesByCategoryName(String categoryName);
}
