package lk.ijse.online_appointment_platform.service.impl;


import jakarta.transaction.Transactional;
import lk.ijse.online_appointment_platform.dto.PayHereResponseDTO;
import lk.ijse.online_appointment_platform.dto.PaymentDTO;
import lk.ijse.online_appointment_platform.entity.Availability;
import lk.ijse.online_appointment_platform.entity.Gig_details;
import lk.ijse.online_appointment_platform.entity.Payment;
import lk.ijse.online_appointment_platform.entity.User;
import lk.ijse.online_appointment_platform.enumClass.AvailabilityStatus;
import lk.ijse.online_appointment_platform.repo.AvailabilityRepository;
import lk.ijse.online_appointment_platform.repo.GigDetailsRepository;
import lk.ijse.online_appointment_platform.repo.PaymentRepository;
import lk.ijse.online_appointment_platform.repo.UserRepository;
import lk.ijse.online_appointment_platform.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private  PaymentRepository paymentRepository;
    @Autowired
    private AvailabilityRepository availabilityRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private GigDetailsRepository gigRepository;

    @Override
    @Transactional
    public Payment makePayment(PaymentDTO dto) {
        // 1. Load availability
        Availability availability = availabilityRepository.findById(dto.getAvailabilityId())
                .orElseThrow(() -> new RuntimeException("Availability not found"));

        // 2. Check availability is not already booked
        if (availability.getStatus() == AvailabilityStatus.COMPLETED) {
            throw new RuntimeException("This slot is already booked");
        }

        // 3. Update status
        availability.setStatus(AvailabilityStatus.COMPLETED);
        availabilityRepository.save(availability);

        // 4. Load related entities
        User user = userRepository.findById(String.valueOf(dto.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        Gig_details gig = gigRepository.findById(dto.getGigId())
                .orElseThrow(() -> new RuntimeException("Gig not found"));

        // 5. Create and save payment
        Payment payment = new Payment();
        payment.setAmount(dto.getAmount());
        payment.setPaidAt(java.time.LocalDateTime.now());
        payment.setUser(user);
        payment.setGig(gig);
        payment.setAvailability(availability);

        return paymentRepository.save(payment);
    }


    @Override
    public void handlePayHereNotification(PayHereResponseDTO dto) {
        if (!"2".equals(dto.getStatus_code())) {
            throw new RuntimeException("Payment failed or incomplete");
        }

        User user = userRepository.findById(String.valueOf(Long.valueOf(dto.getCustom_1())))
                .orElseThrow(() -> new RuntimeException("User not found"));

        Availability availability = availabilityRepository.findById(Long.valueOf(dto.getCustom_2()))
                .orElseThrow(() -> new RuntimeException("Availability not found"));

        if (availability.getStatus() == AvailabilityStatus.COMPLETED) {
            return; // Already processed
        }

        Gig_details gig = gigRepository.findById(Long.valueOf(dto.getCustom_3()))
                .orElseThrow(() -> new RuntimeException("Gig not found"));

        availability.setStatus(AvailabilityStatus.COMPLETED);
        availabilityRepository.save(availability);

        Payment payment = new Payment();
        payment.setAmount(Double.valueOf(dto.getPayhere_amount()));
        payment.setPaidAt(LocalDateTime.now());
        payment.setUser(user);
        payment.setAvailability(availability);
        payment.setGig(gig);

        paymentRepository.save(payment);
    }

}
