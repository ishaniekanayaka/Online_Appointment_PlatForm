package lk.ijse.online_appointment_platform.service.impl;

import lk.ijse.online_appointment_platform.entity.User;
import lk.ijse.online_appointment_platform.entity.VerificationOTP;
import lk.ijse.online_appointment_platform.repo.OTPRepository;
import lk.ijse.online_appointment_platform.repo.UserRepository;
import lk.ijse.online_appointment_platform.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

/*@Service
public class OTPServiceImpl implements OTPService {


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void sendOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);

        VerificationOTP otpVerification = new VerificationOTP();
        otpVerification.setEmail(email);
        otpVerification.setOtp(otp);
        otpVerification.setExpiryTime(expiryTime);
        otpRepository.save(otpVerification);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp + "\nIt will expire in 5 minutes.");
        mailSender.send(message);
    }

    @Override
    public boolean verifyOtp(String email, String otp, String newPassword) {
        Optional<VerificationOTP> otpData = otpRepository.findTopByEmailOrderByExpiryTimeDesc(email);
        if (otpData.isPresent() && otpData.get().getOtp().equals(otp) &&
                otpData.get().getExpiryTime().isAfter(LocalDateTime.now())) {

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userRepository.save(user);
            otpRepository.delete(otpData.get()); // Delete used OTP
            return true;
        }
        return false;
    }*/
@Service
public class OTPServiceImpl implements OTPService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // ✅ Use the configured bean

    @Override
    public void sendOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);

        VerificationOTP otpVerification = new VerificationOTP();
        otpVerification.setEmail(email);
        otpVerification.setOtp(otp);
        otpVerification.setExpiryTime(expiryTime);
        otpRepository.save(otpVerification);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp + "\nIt will expire in 5 minutes.");
        mailSender.send(message);
    }

    @Override
    public boolean verifyOtp(String email, String otp, String newPassword) {
        Optional<VerificationOTP> otpData = otpRepository.findTopByEmailOrderByExpiryTimeDesc(email);
        if (otpData.isPresent() && otpData.get().getOtp().equals(otp) &&
                otpData.get().getExpiryTime().isAfter(LocalDateTime.now())) {

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            user.setPassword(passwordEncoder.encode(newPassword)); // ✅ Use injected encoder
            userRepository.save(user);
            otpRepository.delete(otpData.get()); // Delete used OTP
            return true;
        }
        return false;
    }

}
