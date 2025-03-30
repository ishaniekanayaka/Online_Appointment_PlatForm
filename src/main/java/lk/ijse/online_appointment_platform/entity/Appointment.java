package lk.ijse.online_appointment_platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime date;  // Use LocalDateTime instead of Date for both date and time

    @PrePersist
    protected void onCreate() {
        if (this.date == null) {
            this.date = LocalDateTime.now();  // Sets current date and time automatically
        }
    }

    @ManyToOne
    @JoinColumn(name = "gig_id")  // Foreign key to Gig_details
    private Gig_details gig;

    @Column(nullable = false)
    private LocalDateTime dateTime;  // Use LocalDateTime for date and time

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "appointment")
    private List<Availability> availabilities;

}
