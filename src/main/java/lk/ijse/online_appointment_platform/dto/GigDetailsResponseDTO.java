package lk.ijse.online_appointment_platform.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GigDetailsResponseDTO {
    private Long id;
    private String FullName; // capital F to match the entity
    private String image;
    private String degree;
    private String experience;
    private Double amountCharge;
    private String location;
    private Integer contactNumber; // Integer type to match the entity
    private Integer maxAppointmentsPerDay;
    private boolean active;
    private LocalDateTime dateTime;

    private Integer userId;
    private String categoryName;
    private String subCategoryName;
}

