package lk.ijse.online_appointment_platform.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private Integer star = 0; // Default value


    @PrePersist
    protected void setDefaultDateTime() {
        if (this.dateTime == null) {
            this.dateTime = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void updateDateTime() {
        this.dateTime = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")  // New Subcategory field
    private SubCategory subCategory;

    @OneToMany(mappedBy = "gig")
    @JsonManagedReference
    private List<Review> reviews;

}
