package lk.ijse.online_appointment_platform.controller;


import lk.ijse.online_appointment_platform.dto.PayHereResponseDTO;
import lk.ijse.online_appointment_platform.dto.PaymentDTO;
import lk.ijse.online_appointment_platform.entity.Payment;
import lk.ijse.online_appointment_platform.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController

@RequestMapping("api/v1/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<?> makePayment(@RequestBody PaymentDTO dto) {
        try {
            Payment payment = paymentService.makePayment(dto);
            return ResponseEntity.ok(payment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/payhere-notification")
    public ResponseEntity<String> handlePayHereCallback(@RequestBody PayHereResponseDTO payHereDTO) {
        paymentService.handlePayHereNotification(payHereDTO);
        return ResponseEntity.ok("Payment processed successfully");
    }
}
