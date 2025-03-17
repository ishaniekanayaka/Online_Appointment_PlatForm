package lk.ijse.online_appointment_platform.repo;



import lk.ijse.online_appointment_platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,String> {

   /* User findByEmail(String userName);

    boolean existsByEmail(String userName);

    int deleteByEmail(String userName);*/

    User findByEmail(String userName);

    // Check if user exists by email
    boolean existsByEmail(String userName);

    // Delete user by email (returns number of rows deleted)
    int deleteByEmail(String userName);





}