package lk.ijse.online_appointment_platform.repo;

import lk.ijse.online_appointment_platform.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    // Find subcategories by category ID
    List<SubCategory> findByCategoryId(Long categoryId);

    @Query("SELECT sc.name from  SubCategory sc")
    List<String> getSubCategoryNames();

    @Query("SELECT sc FROM SubCategory sc WHERE sc.name=:name")
    Optional<SubCategory> findByName(@Param("name") String name);
}
