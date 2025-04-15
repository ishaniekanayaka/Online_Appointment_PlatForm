package lk.ijse.online_appointment_platform.service;

import lk.ijse.online_appointment_platform.dto.ProfileDto;

public interface ProfileService {
    public String saveProfile(ProfileDto dto, String fileName);
}
