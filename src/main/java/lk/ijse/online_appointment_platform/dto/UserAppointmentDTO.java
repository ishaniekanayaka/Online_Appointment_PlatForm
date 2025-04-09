package lk.ijse.online_appointment_platform.dto;

import lk.ijse.online_appointment_platform.enumClass.AvailabilityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAppointmentDTO {
    private Long id;
    private String gigName;

    private LocalDateTime dateTime;
    private AvailabilityStatus status;
}