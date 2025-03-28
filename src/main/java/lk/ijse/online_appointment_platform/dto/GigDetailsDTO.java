package lk.ijse.online_appointment_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class GigDetailsDTO {
    private Long id;
    private String fullName;
    private String image;
    private String degree;
    private String experience;
    private Double amountCharge;
    private String location;
    private String contactNumber;
    private Integer maxAppointmentsPerDay;
    private boolean active;
    private LocalDate date;
    private Long userId;
    private Long categoryId;

}
