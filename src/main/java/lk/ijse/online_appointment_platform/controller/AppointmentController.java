package lk.ijse.online_appointment_platform.controller;

import lk.ijse.online_appointment_platform.dto.AppointmentDTO;
import lk.ijse.online_appointment_platform.entity.Appointment;
import lk.ijse.online_appointment_platform.entity.Availability;
import lk.ijse.online_appointment_platform.entity.User;
import lk.ijse.online_appointment_platform.enumClass.AvailabilityStatus;
import lk.ijse.online_appointment_platform.repo.AppointmentRepository;
import lk.ijse.online_appointment_platform.repo.AvailabilityRepository;
import lk.ijse.online_appointment_platform.repo.UserRepository;
import lk.ijse.online_appointment_platform.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {


        @Autowired
        private AppointmentService appointmentService;

        @Autowired
        private AppointmentRepository appointmentRepository;

        @Autowired
        private AvailabilityRepository availabilityRepository;

        @Autowired
        private UserRepository userRepository;

        @PostMapping("/book")
        public ResponseEntity<String> bookAppointment(@RequestBody AppointmentDTO appointmentDTO) {
            try {
                // Book the appointment via the service layer
                appointmentService.bookAppointment(appointmentDTO);

                return new ResponseEntity<>("Appointment successfully booked.", HttpStatus.CREATED);
            } catch (RuntimeException e) {
                // Handle the error appropriately and return a meaningful response
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        // Endpoint to get all appointments for a specific gig
        @GetMapping("/gig/{gigId}")
        public ResponseEntity<List<Appointment>> getAppointmentsForGig(@PathVariable Long gigId) {
            List<Appointment> appointments = appointmentRepository.findByGigId(gigId);
            return new ResponseEntity<>(appointments, HttpStatus.OK);
        }

        // Endpoint to check the availability for a specific gig and time slot
        @GetMapping("/availability")
        public ResponseEntity<Boolean> checkAvailability(@RequestParam Long gigId, @RequestParam String appointmentTime) {
            LocalDateTime requestedTime = LocalDateTime.parse(appointmentTime);

            // Check availability (use the service method to check availability)
            boolean isAvailable = appointmentService.checkAvailability(gigId, requestedTime);
            return new ResponseEntity<>(isAvailable, HttpStatus.OK);
        }

    // Endpoint to accept the booking
    @PutMapping("/accept/{availabilityId}")
    public ResponseEntity<String> acceptBooking(@PathVariable Long availabilityId) {
        try {
            appointmentService.acceptBooking(availabilityId);
            return ResponseEntity.ok("Booking accepted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Availability>> getPendingBookings() {
        List<Availability> pendingBookings = availabilityRepository.findByStatus(AvailabilityStatus.PENDING);
        return ResponseEntity.ok(pendingBookings);
    }

    // Endpoint to cancel the booking
    @PutMapping("/cancel/{availabilityId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long availabilityId) {
        try {
            appointmentService.cancelBooking(availabilityId);
            return ResponseEntity.ok("Booking cancelled successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/appointments/check")
    public ResponseEntity<?> checkAppointment(
            @RequestParam String email,
            @RequestParam Long gigId) {

        // Find user by email
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();

        // Check if appointment exists with this gig
        boolean hasAppointment = appointmentRepository.existsByUserIdAndGigId((long) user.getId(), gigId);
        if (!hasAppointment) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You must have an appointment to give a review");
        }

        // Return user ID to frontend
        return ResponseEntity.ok(Map.of("data", Map.of("userId", user.getId())));
    }


}


