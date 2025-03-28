package lk.ijse.online_appointment_platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Gig_details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String FullName;
    private String image;
    private String degree;
    private String experience;
    private Double amountCharge;
    private String location;
    // Adding contact number
    private Integer contactNumber;

    @Column(nullable = false)
    private Integer maxAppointmentsPerDay;

    private boolean active = false;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "gig")
    private List<Review> reviews;

}
