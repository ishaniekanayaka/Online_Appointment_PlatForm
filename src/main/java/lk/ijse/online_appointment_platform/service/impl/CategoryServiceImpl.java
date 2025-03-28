package lk.ijse.online_appointment_platform.service.impl;

import lk.ijse.online_appointment_platform.dto.CategoryDTO;
import lk.ijse.online_appointment_platform.entity.Category;
import lk.ijse.online_appointment_platform.entity.SubCategory;
import lk.ijse.online_appointment_platform.repo.CategoryRepository;
import lk.ijse.online_appointment_platform.repo.SubCategoryRepository;
import lk.ijse.online_appointment_platform.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ModelMapper modelMapper;

   @Override
   public void addCategory(CategoryDTO categoryDTO){

     categoryRepository.save(modelMapper.map(categoryDTO, Category.class));


   }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {

        categoryRepository.save(modelMapper.map(categoryDTO, Category.class));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
       return modelMapper.map(categoryRepository.findAll(),
               new TypeToken<List<CategoryDTO>>(){}.getType());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
       Category category = categoryRepository.findById(id).orElseThrow(()->
               new RuntimeException("Category does not exists")
               );
       return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<String> getCategoryNames() {
        return categoryRepository.getCategoryNames();
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Customer does not exist"));
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<SubCategory> getSubCategoriesByCategoryId(Long categoryId) {
        // Fetch subcategories for the selected category
        return subCategoryRepository.findByCategoryId(categoryId);
    }

}
