package lk.ijse.online_appointment_platform.repo;



import lk.ijse.online_appointment_platform.dto.UserProfileDTO;
import lk.ijse.online_appointment_platform.entity.SubCategory;
import lk.ijse.online_appointment_platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,String> {

    // Check if user exists by email
    boolean existsByEmail(String userName);

    // Delete user by email (returns number of rows deleted)
    int deleteByEmail(String userName);

    @Query("SELECT u.email FROM User u")
    List<String> getUserEmails();

    // Correct method signature to return Optional<User>
    @Query("SELECT u FROM User u WHERE u.email = :email")
    // Optional<User> findByEmail(@Param("email") String email);

    Optional<User> findByEmail(String email);

    @Query("SELECT new lk.ijse.online_appointment_platform.dto.UserProfileDTO(u.name, u.email, u.dob, u.role, u.active, p.imagePath) " +
            "FROM User u LEFT JOIN u.profile p WHERE u.email = :email")
    UserProfileDTO findUserProfileByEmail(@Param("email") String email);


    //List<User> findByUserId(Integer userId);

}