package lk.ijse.online_appointment_platform.service;

import lk.ijse.online_appointment_platform.dto.GigDetailsDTO;
import lk.ijse.online_appointment_platform.entity.Gig_details;
import lk.ijse.online_appointment_platform.entity.SubCategory;

import java.util.List;

public interface GigService {
    void addGigDetails(GigDetailsDTO gigDetailsDTO);

}
