package lk.ijse.online_appointment_platform.controller;


import lk.ijse.online_appointment_platform.dto.AuthDTO;
import lk.ijse.online_appointment_platform.dto.ResponseDTO;
import lk.ijse.online_appointment_platform.dto.UserDTO;
import lk.ijse.online_appointment_platform.service.OTPService;
import lk.ijse.online_appointment_platform.service.impl.UserServiceImpl;
import lk.ijse.online_appointment_platform.util.JwtUtil;
import lk.ijse.online_appointment_platform.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
//@CrossOrigin("*")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final ResponseDTO responseDTO;

    @Autowired
    private OTPService otpService;

    //constructor injection
    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserServiceImpl userService, ResponseDTO responseDTO) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.responseDTO = responseDTO;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO> authenticate(@RequestBody UserDTO userDTO) {
        try {
            UserDTO loadedUser = userService.loadUserDetailsByUsername(userDTO.getEmail());

            if (loadedUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "User not found", null));
            }

            // Prevent inactive users from logging in
            if (!loadedUser.isActive()) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new ResponseDTO(VarList.Not_Acceptable, "Your account is inactive. Please contact support.", null));
            }

            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));

            // Generate JWT Token
            String token = jwtUtil.generateToken(loadedUser);
            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
            }

            AuthDTO authDTO = new AuthDTO();
            authDTO.setEmail(loadedUser.getEmail());
            authDTO.setToken(token);
            authDTO.setRole(loadedUser.getRole());
            authDTO.setUserId(Math.toIntExact(loadedUser.getId()));

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.Created, "Success", authDTO));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Invalid Credentials", e.getMessage()));
        }
    }

    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, String>> sendOtp(@RequestBody Map<String, String> request) throws MessagingException {
        String email = request.get("email");
        otpService.sendOtp(email);
        return ResponseEntity.ok(Collections.singletonMap("message", "OTP sent."));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");

        if (otpService.verifyOtp(email, otp, newPassword)) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Password updated."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", "Invalid OTP or expired."));
        }

    }


}

