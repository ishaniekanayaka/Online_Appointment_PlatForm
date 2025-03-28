package lk.ijse.online_appointment_platform.repo;

import lk.ijse.online_appointment_platform.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    // Find subcategories by category ID
    List<SubCategory> findByCategoryId(Long categoryId);
}
