package lk.ijse.online_appointment_platform.repo;

import lk.ijse.online_appointment_platform.dto.AvailabilityDTO;
import lk.ijse.online_appointment_platform.entity.Availability;
import lk.ijse.online_appointment_platform.enumClass.AvailabilityStatus;
import lk.ijse.online_appointment_platform.entity.Gig_details;
import lk.ijse.online_appointment_platform.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    @Query("SELECT new lk.ijse.online_appointment_platform.dto.AvailabilityDTO(a.id, a.appointment.dateTime, a.appointment.user.name, a.status) FROM Availability a WHERE a.gig.id = :gigId")
    List<AvailabilityDTO> findByGigId(@Param("gigId") Long gigId);
   /* List<Availability> findByGigId(Long gigId);*/

    List<Availability> findByAppointment(Appointment appointment);

    void deleteByGigId(Long gigId);

    // Check if the gig has any appointments within 30 minutes of the requested time slot
    boolean existsByGigAndAppointmentDateTimeBetween(Gig_details gig, LocalDateTime startTime, LocalDateTime endTime);

    // Find all Availability records with the status PENDING
    List<Availability> findByStatus(AvailabilityStatus status);
}
