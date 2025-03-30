package lk.ijse.online_appointment_platform.repo;

import lk.ijse.online_appointment_platform.entity.Availability;
import lk.ijse.online_appointment_platform.entity.AvailabilityStatus;
import lk.ijse.online_appointment_platform.entity.Gig_details;
import lk.ijse.online_appointment_platform.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    List<Availability> findByGigId(Long gigId);

    List<Availability> findByAppointment(Appointment appointment);

    void deleteByGigId(Long gigId);

    // Check if the gig has any appointments within 30 minutes of the requested time slot
    boolean existsByGigAndAppointmentDateTimeBetween(Gig_details gig, LocalDateTime startTime, LocalDateTime endTime);

    // Find all Availability records with the status PENDING
    List<Availability> findByStatus(AvailabilityStatus status);
}
