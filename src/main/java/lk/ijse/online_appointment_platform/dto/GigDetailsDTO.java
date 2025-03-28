package lk.ijse.online_appointment_platform.dto;

import jakarta.validation.constraints.NotNull;
import lk.ijse.online_appointment_platform.entity.Category;
import lk.ijse.online_appointment_platform.entity.User;
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
    @NotNull(message = "User ID cannot be null")
    private Integer userId;

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;


    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(String dateStr) {
        this.date = (dateStr == null || dateStr.isEmpty()) ? LocalDate.now() : LocalDate.parse(dateStr);
    }

}
