package lk.ijse.online_appointment_platform.service;

import lk.ijse.online_appointment_platform.dto.AppointmentDTO;
import lk.ijse.online_appointment_platform.dto.GigDetailsResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    public boolean checkAvailability(Long gigId, LocalDateTime requestedTime);
    public void bookAppointment(AppointmentDTO appointmentDTO);
    public void acceptBooking(Long availabilityId);
    public void completeBooking(Long availabilityId);
    public void cancelBooking(Long availabilityId);

}
