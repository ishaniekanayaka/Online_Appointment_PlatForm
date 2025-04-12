package lk.ijse.online_appointment_platform.repo;

import lk.ijse.online_appointment_platform.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
