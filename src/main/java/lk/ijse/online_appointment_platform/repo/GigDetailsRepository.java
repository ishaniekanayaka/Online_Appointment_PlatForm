package lk.ijse.online_appointment_platform.repo;

import lk.ijse.online_appointment_platform.entity.Gig_details;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GigDetailsRepository extends JpaRepository<Gig_details, Long> {
    Optional<Gig_details> findById(Long gigId);
}
