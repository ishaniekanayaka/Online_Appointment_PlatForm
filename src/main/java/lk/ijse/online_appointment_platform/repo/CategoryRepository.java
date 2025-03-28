package lk.ijse.online_appointment_platform.repo;

import lk.ijse.online_appointment_platform.dto.CategoryDTO;
import lk.ijse.online_appointment_platform.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c.name from Category c")
    List<String> getCategoryNames();

    @Query("SELECT c FROM Category c WHERE c.name=:name")
    Optional<Category> findByName(@Param("name") String name);


}
