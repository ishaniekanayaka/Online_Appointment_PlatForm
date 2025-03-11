package lk.ijse.online_appointment_platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "systemuser")
@Data
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(unique = true)
    private String email;
    private String dob;
    private String password;


    @OneToMany(mappedBy = "user")
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

  /*  @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;*/

}
