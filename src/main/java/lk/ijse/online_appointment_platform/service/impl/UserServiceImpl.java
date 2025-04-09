package lk.ijse.online_appointment_platform.service.impl;

import lk.ijse.online_appointment_platform.dto.UserAppointmentDTO;
import lk.ijse.online_appointment_platform.dto.UserDTO;
import lk.ijse.online_appointment_platform.entity.Gig_details;
import lk.ijse.online_appointment_platform.entity.User;
import lk.ijse.online_appointment_platform.repo.AvailabilityRepository;
import lk.ijse.online_appointment_platform.repo.GigDetailsRepository;
import lk.ijse.online_appointment_platform.repo.UserRepository;
import lk.ijse.online_appointment_platform.service.UserService;
import lk.ijse.online_appointment_platform.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GigDetailsRepository gigDetailsRepository;
    @Autowired
    private AvailabilityRepository availabilityRepository;


    @Autowired
    private JavaMailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), getAuthority(user)
        );
    }

    public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return modelMapper.map(user, UserDTO.class);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }

    @Override
    public UserDTO searchUser(String username) {
        return userRepository.findByEmail(username)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElse(null);
    }

    @Override
    public int saveUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            userRepository.save(modelMapper.map(userDTO, User.class));
            return VarList.Created;
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }


   @Override
   public boolean changeUserStatus(int id, boolean status) {
       Optional<User> userOptional = userRepository.findById(String.valueOf(id));  // Ensure you're using the correct ID type
       if (userOptional.isPresent()) {
           User user = userOptional.get();
           user.setActive(status); // Update the active status of the user
           userRepository.save(user);  // Save the updated user back to the DB

           // If user is activated, send the activation email
           if (status) {
               sendActivationEmail(user.getEmail(), user.getName()); // Send the email to the correct user
           }

           return true;
       }
       return false;  // Return false if user is not found
   }

    private void sendActivationEmail(String email, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);  // Send the email to the activated user's email
        message.setSubject("Your Account Has Been Activated");
        message.setText("Hello " + name + ",\n\nYour account on Online Appointment Platform has been successfully activated.\n\nYou can now log in and enjoy our services.\n\nThank you!");
        mailSender.send(message);  // Send the email
    }


    @Override
    public boolean updateUser(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findById(String.valueOf(userDTO.getId()));
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setDob(userDTO.getDob());

            if (!userDTO.getPassword().isEmpty()) { // Update password only if provided
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            user.setRole(userDTO.getRole());
            user.setActive(userDTO.isActive());

            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public List<String> getUserEmails() {
        return userRepository.getUserEmails();
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        // Debugging logs
        System.out.println("User search for email: " + email);
        userOptional.ifPresentOrElse(
                user -> System.out.println("User found: " + user.getEmail()),
                () -> System.out.println("User not found!")
        );

        return userOptional.map(user -> modelMapper.map(user, UserDTO.class)).orElse(null);
    }

    @Override
    public List<Gig_details> getGigsByUserId(Long userId) {
        return gigDetailsRepository.findByUserId(userId) ;
    }
}