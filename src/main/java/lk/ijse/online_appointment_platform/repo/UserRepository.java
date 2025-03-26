package lk.ijse.online_appointment_platform.repo;



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

}