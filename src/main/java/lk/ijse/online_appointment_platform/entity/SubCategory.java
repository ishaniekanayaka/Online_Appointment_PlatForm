package lk.ijse.online_appointment_platform.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String image;

    @ManyToOne
    @JsonBackReference // Prevent circular reference
    @JoinColumn(name = "category_id")
    private Category category;

    public Category getCategory() {
        return this.category;
    }

  /*  @OneToMany(mappedBy = "subCategory")
    private List<Gig> gigs;
*/
}
