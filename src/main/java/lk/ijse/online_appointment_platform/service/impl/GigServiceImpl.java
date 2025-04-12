package lk.ijse.online_appointment_platform.service.impl;

import lk.ijse.online_appointment_platform.dto.AvailabilityDTO;
import lk.ijse.online_appointment_platform.dto.GigDetailsDTO;
import lk.ijse.online_appointment_platform.dto.GigDetailsResponseDTO;
import lk.ijse.online_appointment_platform.dto.UserAppointmentDTO;
import lk.ijse.online_appointment_platform.entity.*;
import lk.ijse.online_appointment_platform.repo.*;
import lk.ijse.online_appointment_platform.service.GigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GigServiceImpl implements GigService {
    @Autowired
    private GigDetailsRepository gigDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Override
    public void addGigDetails(GigDetailsDTO gigDetailsDTO) {
        // Validate if subCategoryId is present
        if (gigDetailsDTO.getSubCategoryId() == null) {
            throw new IllegalArgumentException("SubCategory ID must not be null");
        }

        // Fetch the SubCategory based on the subCategoryId (Long)
        Optional<SubCategory> subCategoryOptional = subCategoryRepository.findById(gigDetailsDTO.getSubCategoryId());

        if (subCategoryOptional.isPresent()) {
            // If the subcategory exists, get the associated category
            SubCategory subCategory = subCategoryOptional.get();
            Category category = subCategory.getCategory();  // Get the associated category

            // Fetch the User based on the userId (Integer)
            Optional<User> userOptional = userRepository.findById(String.valueOf(gigDetailsDTO.getUserId()));

            if (userOptional.isPresent()) {
                // If the user exists, create a new Gig_details object
                Gig_details gigDetails = new Gig_details();

                // Set the fields from the DTO
                gigDetails.setFullName(gigDetailsDTO.getFullName());
                gigDetails.setImage(gigDetailsDTO.getImage());
                gigDetails.setDegree(gigDetailsDTO.getDegree());
                gigDetails.setExperience(gigDetailsDTO.getExperience());
                gigDetails.setAmountCharge(gigDetailsDTO.getAmountCharge());
                gigDetails.setLocation(gigDetailsDTO.getLocation());
                gigDetails.setContactNumber(Integer.valueOf(gigDetailsDTO.getContactNumber()));
                gigDetails.setMaxAppointmentsPerDay(gigDetailsDTO.getMaxAppointmentsPerDay());
                gigDetails.setActive(gigDetailsDTO.isActive());

                // Handle the date safely
                LocalDateTime dateTime = gigDetailsDTO.getDateTime(); // Already LocalDateTime
                gigDetails.setDateTime(dateTime);
                gigDetails.setSubCategory(subCategory);
                // Set the category (associated with the subcategory) and user relations
                gigDetails.setCategory(category);  // Set the category based on subcategory
                gigDetails.setUser(userOptional.get()); // Linking gig with the user

                // Save the gigDetails to the database
                gigDetailsRepository.save(gigDetails);
            } else {
                throw new RuntimeException("User not found for the provided userId.");
            }
        } else {
            throw new RuntimeException("SubCategory not found for the provided subCategoryId.");
        }
    }

   /* @Override
    public List<Availability> getAppointmentsByGigId(Long gigId) {
        return availabilityRepository.findByGigId(gigId);
    }
*/
   @Override
   public List<AvailabilityDTO> getAppointmentsByGigId(Long gigId) {
       return availabilityRepository.findByGigId(gigId);
   }

    public List<UserAppointmentDTO> getAppointmentsByUserId(Long userId) {
        return availabilityRepository.findAppointmentsByUserId(userId);
    }

    @Override
    public List<GigDetailsResponseDTO> getAllGigs() {
        return gigDetailsRepository.getAllGigDetailsWithNames();
    }

}
