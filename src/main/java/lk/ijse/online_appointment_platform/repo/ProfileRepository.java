package lk.ijse.online_appointment_platform.repo;

import lk.ijse.online_appointment_platform.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Optional<Profile> findByUserId(int userId);
}

