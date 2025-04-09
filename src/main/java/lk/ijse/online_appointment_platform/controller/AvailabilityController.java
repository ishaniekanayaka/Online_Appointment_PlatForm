package lk.ijse.online_appointment_platform.controller;


import lk.ijse.online_appointment_platform.dto.AvailabilityDTO;
import lk.ijse.online_appointment_platform.dto.GigAppointmentDTO;
import lk.ijse.online_appointment_platform.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @GetMapping("/all")
    public ResponseEntity<List<GigAppointmentDTO>> getAllAvailabilityDetails() {
        List<GigAppointmentDTO> list = availabilityService.getAllAvailabilityDetails();
        return ResponseEntity.ok(list);
    }
}
