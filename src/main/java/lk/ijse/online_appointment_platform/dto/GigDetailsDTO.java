package lk.ijse.online_appointment_platform.dto;

import jakarta.validation.constraints.NotNull;
import lk.ijse.online_appointment_platform.entity.Category;
import lk.ijse.online_appointment_platform.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


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

    private LocalDateTime dateTime;  // Changed from LocalDate to LocalDateTime


    private Integer userId;


    private Long categoryId;

   // New field for Subcategory ID
    private Long subCategoryId;

    // Auto-set the dateTime if not provided
    public LocalDateTime getDateTime() {
        return (dateTime == null) ? LocalDateTime.now() : dateTime;
    }

}
