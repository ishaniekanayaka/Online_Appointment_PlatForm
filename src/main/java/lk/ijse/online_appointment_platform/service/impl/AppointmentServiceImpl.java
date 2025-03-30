package lk.ijse.online_appointment_platform.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.online_appointment_platform.dto.AppointmentDTO;
import lk.ijse.online_appointment_platform.entity.*;
import lk.ijse.online_appointment_platform.repo.AppointmentRepository;
import lk.ijse.online_appointment_platform.repo.AvailabilityRepository;
import lk.ijse.online_appointment_platform.repo.GigDetailsRepository;
import lk.ijse.online_appointment_platform.repo.UserRepository;
import lk.ijse.online_appointment_platform.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private GigDetailsRepository gigDetailsRepository;


    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void bookAppointment(AppointmentDTO appointmentDTO) {
        // Check if the gig exists
        Gig_details gigDetails = gigDetailsRepository.findById(appointmentDTO.getGigId())
                .orElseThrow(() -> new RuntimeException("Gig not found"));

        // Check if the user exists
        User user = userRepository.findById(String.valueOf(appointmentDTO.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        // If the appointmentTime is not provided, set the current time
        LocalDateTime appointmentTime = appointmentDTO.getAppointmentTime() != null
                ? appointmentDTO.getAppointmentTime()
                : LocalDateTime.now();

        // Check if the requested time is available for the gig with a 5-minute gap
        boolean isAvailable = checkAvailability(appointmentDTO.getGigId(), appointmentTime);

        if (!isAvailable) {
            throw new RuntimeException("Requested time is not available for this gig. Please choose a time with at least a 5-minute gap.");
        }

        // Check if the gig has reached its max appointments for the day
        if (hasReachedMaxAppointmentsForTheDay(gigDetails)) {
            throw new RuntimeException("Gig has reached the maximum number of appointments for today.");
        }

        // Create and save the new appointment
        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setGig(gigDetails);
        appointment.setDateTime(appointmentTime);  // Use the determined appointment time

        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Create the Availability entry and set status as PENDING
        Availability availability = new Availability();
        availability.setAppointment(savedAppointment);
        availability.setGig(gigDetails);
        availability.setStatus(AvailabilityStatus.PENDING);  // Set status to PENDING initially

        availabilityRepository.save(availability);

        // Decrement the maxAppointmentsPerDay by 1 after booking the appointment
        gigDetails.setMaxAppointmentsPerDay(gigDetails.getMaxAppointmentsPerDay() - 1);
        gigDetailsRepository.save(gigDetails);

        // Log for debugging purposes to confirm the update
        System.out.println("Updated Gig Details: " + gigDetails.getMaxAppointmentsPerDay());
    }


    // Check if the gig has reached the max appointments for today
    private boolean hasReachedMaxAppointmentsForTheDay(Gig_details gigDetails) {
        int currentAppointments = appointmentRepository.countByGigIdAndDateTimeBetween(
                gigDetails.getId(),
                LocalDateTime.now().toLocalDate().atStartOfDay(),
                LocalDateTime.now().toLocalDate().atTime(23, 59)
        );

        return currentAppointments >= gigDetails.getMaxAppointmentsPerDay();
    }

    @Transactional
    public void completeBooking(Long availabilityId) {
        // Retrieve the Availability object by ID
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new RuntimeException("Availability not found"));

        // Check if the current status is BOOKING (only completed appointments should have this status)
        if (availability.getStatus() != AvailabilityStatus.BOOKING) {
            throw new RuntimeException("This appointment cannot be completed because it's not booked yet.");
        }

        // Update the status to COMPLETED
        availability.setStatus(AvailabilityStatus.COMPLETED);
        availabilityRepository.save(availability);

        // Optionally, log the status change
        System.out.println("Booking completed, status changed to: " + AvailabilityStatus.COMPLETED);
    }


    @Transactional
    public void acceptBooking(Long availabilityId) {
        // Retrieve the Availability object by ID
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new RuntimeException("Availability not found"));

        // Check if the status is PENDING (booking can't be accepted unless it's pending)
        if (availability.getStatus() != AvailabilityStatus.PENDING) {
            throw new RuntimeException("This booking can't be accepted because it's not pending.");
        }

        // Update the status to BOOKING (gig accepted)
        availability.setStatus(AvailabilityStatus.BOOKING);
        availabilityRepository.save(availability);

        // Optionally, log the status change
        System.out.println("Booking accepted, status changed to: " + AvailabilityStatus.BOOKING);
    }

    // Cancel a booking
    @Transactional
    public void cancelBooking(Long availabilityId) {
        // Retrieve the Availability object by ID
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new RuntimeException("Availability not found"));

        // Check if the status is already CANCELLED or COMPLETED, so we can't cancel it again
        if (availability.getStatus() == AvailabilityStatus.CANCELLED || availability.getStatus() == AvailabilityStatus.COMPLETED) {
            throw new RuntimeException("This booking has already been cancelled or completed.");
        }

        // Update the status to CANCELLED
        availability.setStatus(AvailabilityStatus.CANCELLED);
        availabilityRepository.save(availability);

        // Optionally, log the status change
        System.out.println("Booking cancelled, status changed to: " + AvailabilityStatus.CANCELLED);
    }


    /*@Transactional
    public void acceptAppointment(Long availabilityId) {
        // Retrieve the Availability object
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new RuntimeException("Availability not found"));

        // Check if the current status is PENDING
        if (availability.getStatus() != AvailabilityStatus.PENDING) {
            throw new RuntimeException("This appointment cannot be accepted because it's not pending.");
        }

        // Update the status to BOOKING when the gig holder accepts the appointment
        availability.setStatus(AvailabilityStatus.BOOKING);
        availabilityRepository.save(availability);

        // Optionally, log the status change
        System.out.println("Appointment accepted, status changed to: " + AvailabilityStatus.BOOKING);
    }*/



    public boolean checkAvailability(Long gigId, LocalDateTime requestedTime) {
        Gig_details gigDetails = gigDetailsRepository.findById(gigId)
                .orElseThrow(() -> new RuntimeException("Gig not found"));

        // Check if there's an existing appointment within 5 minutes before or after the requested time
        List<Appointment> appointments = appointmentRepository.findByGigIdAndDateTimeBetween(
                gigId,
                requestedTime.minusMinutes(5),  // 5 minutes before the requested time
                requestedTime.plusMinutes(5)    // 5 minutes after the requested time
        );

        // If there is an appointment in this window, it's not available
        return appointments.isEmpty();
    }


    // Update the Gig's appointment count
    private void updateGigAppointments(Gig_details gigDetails) {
        // Assuming that maxAppointmentsPerDay is set for each gig
        int currentAppointments = appointmentRepository.countByGigIdAndDateTimeBetween(
                gigDetails.getId(),
                LocalDateTime.now().toLocalDate().atStartOfDay(),
                LocalDateTime.now().toLocalDate().atTime(23, 59)
        );

        if (currentAppointments >= gigDetails.getMaxAppointmentsPerDay()) {
            throw new RuntimeException("Gig has reached the maximum number of appointments for today.");
        }
    }
}
