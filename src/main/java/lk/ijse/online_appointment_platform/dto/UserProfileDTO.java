package lk.ijse.online_appointment_platform.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDTO {
    private String name;
    private String email;
    private String dob;
    private String role;
    private boolean active;
    private String imagePath;
}
