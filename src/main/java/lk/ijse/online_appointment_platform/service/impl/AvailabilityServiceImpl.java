package lk.ijse.online_appointment_platform.service.impl;

import lk.ijse.online_appointment_platform.dto.AvailabilityDTO;
import lk.ijse.online_appointment_platform.dto.GigAppointmentDTO;
import lk.ijse.online_appointment_platform.repo.AvailabilityRepository;
import lk.ijse.online_appointment_platform.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;
    @Override
    public List<GigAppointmentDTO> getAllAvailabilityDetails() {
        return availabilityRepository.findAllAvailabilityDetails();
    }
}
