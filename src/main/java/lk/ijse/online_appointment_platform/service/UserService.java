package lk.ijse.online_appointment_platform.service;


import lk.ijse.online_appointment_platform.dto.UserDTO;

public interface UserService {
    int saveUser(UserDTO userDTO);
    UserDTO searchUser(String username);

}