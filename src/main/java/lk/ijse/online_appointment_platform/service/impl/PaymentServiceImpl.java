package lk.ijse.online_appointment_platform.service.impl;


import jakarta.transaction.Transactional;
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
}
