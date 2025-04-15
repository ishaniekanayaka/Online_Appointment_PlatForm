package lk.ijse.online_appointment_platform.service.impl;

import lk.ijse.online_appointment_platform.dto.ProfileDto;
import lk.ijse.online_appointment_platform.entity.Profile;
import lk.ijse.online_appointment_platform.entity.User;
import lk.ijse.online_appointment_platform.repo.ProfileRepository;
import lk.ijse.online_appointment_platform.repo.UserRepository;
import lk.ijse.online_appointment_platform.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProfileRepository profileRepo;

    @Override
    public String saveProfile(ProfileDto dto, String fileName) {
        Optional<User> userOpt = userRepo.findById(String.valueOf(dto.getUserId()));

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            Profile profile = new Profile();
            profile.setUser(user);
            profile.setImagePath("uploads/" + fileName); // Save relative path

            profileRepo.save(profile);

            return profile.getImagePath();
        } else {
            throw new RuntimeException("User not found!");
        }
    }
}
