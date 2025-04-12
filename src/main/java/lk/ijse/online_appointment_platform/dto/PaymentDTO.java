package lk.ijse.online_appointment_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO {
    private Long userId;
    private Long gigId;
    private Long availabilityId;
    private Double amount;
}
