package lk.ijse.online_appointment_platform.dto;

import lk.ijse.online_appointment_platform.entity.Gig_details;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class AuthDTO {
    private String email;
    private String token;
    private String role;
    private Integer userId;
    private Gig_details gigDetails;
}