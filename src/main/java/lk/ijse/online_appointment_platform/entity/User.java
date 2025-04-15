package lk.ijse.online_appointment_platform.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String dob;
    private String password;
    private String role;
    private boolean active = false;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;


    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Gig_details> gigs;

   /* @OneToMany(mappedBy = "user")
    private List<Category> categories;*/

  /*  @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;*/

}