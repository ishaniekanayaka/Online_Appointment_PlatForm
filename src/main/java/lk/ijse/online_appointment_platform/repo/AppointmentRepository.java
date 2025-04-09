package lk.ijse.online_appointment_platform.repo;

import lk.ijse.online_appointment_platform.dto.AppointmentDTO;
import lk.ijse.online_appointment_platform.dto.AvailabilityDTO;
import lk.ijse.online_appointment_platform.dto.GigAppointmentDTO;
import lk.ijse.online_appointment_platform.entity.Appointment;
import lk.ijse.online_appointment_platform.entity.Gig_details;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByGigId(Long gigId);

    List<Appointment> findByGigIdAndDateTimeBetween(Long gigId, LocalDateTime startTime, LocalDateTime endTime);

    int countByGigIdAndDateTimeBetween(Long gigId, LocalDateTime startTime, LocalDateTime endTime);
}