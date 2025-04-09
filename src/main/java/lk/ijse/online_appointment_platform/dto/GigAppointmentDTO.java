package lk.ijse.online_appointment_platform.dto;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Nonnull
@Data
public class GigAppointmentDTO {
    private Long id;
    private String gigName;
    private String userName;
    private LocalDateTime dateTime;
    private String availabilityStatus;
}
