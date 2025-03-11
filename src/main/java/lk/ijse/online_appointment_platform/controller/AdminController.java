
package lk.ijse.online_appointment_platform.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/user")
@CrossOrigin("*")
public class AdminController {

   /* @GetMapping("/test1")
    public String check() {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String password = userDetails.getPassword(); // Get the password (ensure it's stored properly)

            // Check if the password starts with "AAA" and has at least 4 more characters
            if (password != null && password.startsWith("UU") && password.length() >= 5) {
                return "passed~!1";
            }
        }
        return "Access Denied";
    }*/

    @GetMapping("/search")
    public String searchUser() {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String password = userDetails.getPassword(); // Get the password

            // Check if the password starts with "UU"
            if (password != null && password.startsWith("UU") && password.length() >= 5) {
                return "User is authenticated";
            }
        }
        return "Access Denied";
    }
}


