package lk.ijse.online_appointment_platform.dto;

import jakarta.annotation.Nonnull;
import lk.ijse.online_appointment_platform.enumClass.AvailabilityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GigAppointmentDTO {
    private Long id;
    private LocalDateTime dateTime;

    private String userFullName;
    private AvailabilityStatus status;
    private String gigName;
}
