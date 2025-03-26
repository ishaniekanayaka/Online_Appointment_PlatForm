package lk.ijse.online_appointment_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubCategoryDTO {
    private Long id;
    private String name;
    private String description;
    private String image;
    private Long categoryId;
    private String categoryName;
}
