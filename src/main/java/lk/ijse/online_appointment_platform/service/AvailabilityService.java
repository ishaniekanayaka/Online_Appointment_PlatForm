package lk.ijse.online_appointment_platform.service;

import lk.ijse.online_appointment_platform.dto.AvailabilityDTO;
import lk.ijse.online_appointment_platform.dto.GigAppointmentDTO;

import java.util.List;

public interface AvailabilityService {
    List<GigAppointmentDTO> getAllAvailabilityDetails();

}
