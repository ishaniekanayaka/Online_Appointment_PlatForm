package lk.ijse.online_appointment_platform.service;


import lk.ijse.online_appointment_platform.dto.UserDTO;

import java.util.List;

public interface UserService {
    int saveUser(UserDTO userDTO);
    UserDTO searchUser(String username);
    public List<UserDTO> getAllUsers();
    public boolean changeUserStatus(int id, boolean status);
}