package lk.ijse.online_appointment_platform.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public class picEncoder {
    public static String generatePicture(MultipartFile profilePic){
        try {
            return Base64.getEncoder().encodeToString(profilePic.getBytes());  //converted to base64
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
