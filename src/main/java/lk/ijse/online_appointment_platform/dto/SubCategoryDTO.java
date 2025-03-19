package lk.ijse.online_appointment_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubCategoryDTO {
    private String id;
    private String name;
    private String description;
    private String image;
    private String categoryId;
    private String categoryName;
}
