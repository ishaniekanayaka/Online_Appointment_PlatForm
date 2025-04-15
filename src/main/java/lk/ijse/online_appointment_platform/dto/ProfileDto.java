package lk.ijse.online_appointment_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfileDto {
    private int userId;
   /* private String imageFile;*/
    private MultipartFile imageFile;
}
