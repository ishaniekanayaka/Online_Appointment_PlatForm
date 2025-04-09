package lk.ijse.online_appointment_platform.dto;

import lk.ijse.online_appointment_platform.enumClass.AvailabilityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AvailabilityDTO {
    private Long id;
    private LocalDateTime dateTime;

    private String userFullName;
    private AvailabilityStatus status;

}