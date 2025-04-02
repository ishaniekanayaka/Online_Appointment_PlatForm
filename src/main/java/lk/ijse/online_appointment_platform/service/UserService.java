
package lk.ijse.online_appointment_platform.service;



import lk.ijse.online_appointment_platform.dto.UserAppointmentDTO;
import lk.ijse.online_appointment_platform.dto.UserDTO;
import lk.ijse.online_appointment_platform.entity.Gig_details;

import java.util.List;

public interface UserService {
    int saveUser(UserDTO userDTO);
    UserDTO searchUser(String username);
    List<UserDTO> getAllUsers();
    boolean changeUserStatus(int id, boolean status);
    boolean updateUser(UserDTO userDTO);
    List<String>getUserEmails();
    UserDTO getUserByEmail(String email);
    List<Gig_details> getGigsByUserId(Long userId);

  //  List<Integer> getUserIds();

}
