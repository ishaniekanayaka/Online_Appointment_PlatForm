package lk.ijse.online_appointment_platform.repo;

import lk.ijse.online_appointment_platform.entity.VerificationOTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<VerificationOTP, Long> {

    Optional<VerificationOTP> findTopByEmailOrderByExpiryTimeDesc(String email);
}
