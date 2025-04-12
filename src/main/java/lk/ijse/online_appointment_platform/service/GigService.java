package lk.ijse.online_appointment_platform.service;

import lk.ijse.online_appointment_platform.dto.AvailabilityDTO;
import lk.ijse.online_appointment_platform.dto.GigDetailsDTO;
import lk.ijse.online_appointment_platform.dto.GigDetailsResponseDTO;
import lk.ijse.online_appointment_platform.dto.UserAppointmentDTO;
import lk.ijse.online_appointment_platform.entity.Appointment;
import lk.ijse.online_appointment_platform.entity.Availability;
import lk.ijse.online_appointment_platform.entity.Gig_details;
import lk.ijse.online_appointment_platform.entity.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GigService {
    void addGigDetails(GigDetailsDTO gigDetailsDTO);
    List<AvailabilityDTO> getAppointmentsByGigId(Long gigId);
    public List<UserAppointmentDTO> getAppointmentsByUserId(Long userId);
   // public List<GigDetailsResponseDTO> getAllGigs();
  // public Optional<GigDetailsResponseDTO> getGigDetailsByGigId(Long gigId);
   public Optional<GigDetailsResponseDTO> getGigDetailsByEmail(String email);
}
