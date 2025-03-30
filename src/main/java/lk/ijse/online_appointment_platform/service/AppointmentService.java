package lk.ijse.online_appointment_platform.service;

import lk.ijse.online_appointment_platform.dto.AppointmentDTO;

import java.time.LocalDateTime;

public interface AppointmentService {
    public boolean checkAvailability(Long gigId, LocalDateTime requestedTime);
    public void bookAppointment(AppointmentDTO appointmentDTO);

}
