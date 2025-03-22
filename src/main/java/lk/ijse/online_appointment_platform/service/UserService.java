package lk.ijse.online_appointment_platform.service;


import lk.ijse.online_appointment_platform.dto.UserDTO;

import java.util.List;

public interface UserService {
    int saveUser(UserDTO userDTO);
    UserDTO searchUser(String username);
     List<UserDTO> getAllUsers();
    boolean changeUserStatus(int id, boolean status);
    boolean updateUser(UserDTO userDTO);
}