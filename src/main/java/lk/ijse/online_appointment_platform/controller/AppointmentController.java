package lk.ijse.online_appointment_platform.controller;

import lk.ijse.online_appointment_platform.dto.AppointmentDTO;
import lk.ijse.online_appointment_platform.entity.Appointment;
import lk.ijse.online_appointment_platform.repo.AppointmentRepository;
import lk.ijse.online_appointment_platform.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {


        @Autowired
        private AppointmentService appointmentService;

        @Autowired
        private AppointmentRepository appointmentRepository;

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
    }


