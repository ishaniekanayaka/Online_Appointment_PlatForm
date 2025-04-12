package lk.ijse.online_appointment_platform.service;

import lk.ijse.online_appointment_platform.dto.PaymentDTO;
import lk.ijse.online_appointment_platform.entity.Payment;

public interface PaymentService {
    public Payment makePayment(PaymentDTO dto);
}
