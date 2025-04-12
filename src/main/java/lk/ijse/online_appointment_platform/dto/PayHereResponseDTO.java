package lk.ijse.online_appointment_platform.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PayHereResponseDTO {
    private String merchant_id;
    private String order_id;
    private String payment_id;
    private String status_code;
    private String payhere_amount;
    private String custom_1; // userId
    private String custom_2; // availabilityId
    private String custom_3; // gigId
}
