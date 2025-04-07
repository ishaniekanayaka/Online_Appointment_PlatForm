package lk.ijse.online_appointment_platform.service;

public interface OTPService {
    void sendOtp(String email);

    boolean verifyOtp(String email, String otp, String newPassword);
}
