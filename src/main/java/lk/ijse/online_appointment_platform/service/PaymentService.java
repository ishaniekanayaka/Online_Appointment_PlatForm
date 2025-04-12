package lk.ijse.online_appointment_platform.service;

import lk.ijse.online_appointment_platform.dto.PayHereResponseDTO;
import lk.ijse.online_appointment_platform.dto.PaymentDTO;
import lk.ijse.online_appointment_platform.entity.Payment;

import java.util.Map;

public interface PaymentService {
    public Payment makePayment(PaymentDTO dto);
  //  public void handlePayHereNotification(Map<String, String> params);
  public void handlePayHereNotification(PayHereResponseDTO dto);
}
