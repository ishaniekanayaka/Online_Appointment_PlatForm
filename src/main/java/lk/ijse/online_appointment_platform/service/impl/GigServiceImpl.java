package lk.ijse.online_appointment_platform.service.impl;

import lk.ijse.online_appointment_platform.dto.GigDetailsDTO;
import lk.ijse.online_appointment_platform.entity.Category;
import lk.ijse.online_appointment_platform.entity.Gig_details;
import lk.ijse.online_appointment_platform.entity.User;
import lk.ijse.online_appointment_platform.repo.CategoryRepository;
import lk.ijse.online_appointment_platform.repo.GigDetailsRepository;
import lk.ijse.online_appointment_platform.repo.SubCategoryRepository;
import lk.ijse.online_appointment_platform.repo.UserRepository;
import lk.ijse.online_appointment_platform.service.GigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    public void addGigDetails(GigDetailsDTO gigDetailsDTO) {
        // Validate if categoryId and userId are present
        if (gigDetailsDTO.getCategoryId() == null || gigDetailsDTO.getUserId() == null) {
            throw new IllegalArgumentException("Category ID and User ID must not be null");
        }

        // Fetch the Category based on the categoryId (Long)
        Optional<Category> categoryOptional = categoryRepository.findById(gigDetailsDTO.getCategoryId());

        // Fetch the User based on the userId (Integer)
        Optional<User> userOptional = userRepository.findById(String.valueOf(gigDetailsDTO.getUserId())); // Make sure to convert properly if needed

        if (categoryOptional.isPresent() && userOptional.isPresent()) {
            // If both the category and user exist, create a new Gig_details object
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
            LocalDate date = gigDetailsDTO.getDate(); // No need to parse, it’s already LocalDate
            gigDetails.setDate(date);

            // Set the category and user relations
            gigDetails.setCategory(categoryOptional.get());
            gigDetails.setUser(userOptional.get()); // Linking gig with the user

            // Save the gigDetails to the database
            gigDetailsRepository.save(gigDetails);
        } else {
            throw new RuntimeException("Category or User not found for the provided IDs.");
        }
    }

}
