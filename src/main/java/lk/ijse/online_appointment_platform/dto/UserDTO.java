package lk.ijse.online_appointment_platform.dto;

import jakarta.persistence.*;
import lk.ijse.online_appointment_platform.entity.Appointment;
import lk.ijse.online_appointment_platform.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String dob;
    private String password;


   /* @OneToMany(mappedBy = "user")
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;*/

  /*  @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;*/

}
