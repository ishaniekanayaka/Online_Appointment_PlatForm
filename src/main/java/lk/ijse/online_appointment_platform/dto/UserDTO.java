package lk.ijse.online_appointment_platform.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String dob;
    private String password;
    private String role;
   //private boolean active;

}
