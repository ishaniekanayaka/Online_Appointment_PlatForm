package lk.ijse.online_appointment_platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore  // 🔥 Add this line
    private User user;

    @ManyToOne
    @JoinColumn(name = "gig_id")
    @JsonIgnore  // Optional: only if you don’t want nested gig data
    private Gig_details gig;


}
