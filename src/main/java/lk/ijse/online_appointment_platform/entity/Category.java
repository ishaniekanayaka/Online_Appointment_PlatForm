package lk.ijse.online_appointment_platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Category {
    @Id

    private String id;
    private String name;
    private String description;
    private String image;

   /* @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;*/

    @OneToMany(mappedBy = "category")
    private List<SubCategory> subCategories;

}
