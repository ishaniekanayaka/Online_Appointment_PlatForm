package lk.ijse.online_appointment_platform.repo;

import lk.ijse.online_appointment_platform.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
