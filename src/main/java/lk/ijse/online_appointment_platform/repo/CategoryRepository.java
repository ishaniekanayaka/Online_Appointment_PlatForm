package lk.ijse.online_appointment_platform.repo;

import lk.ijse.online_appointment_platform.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
