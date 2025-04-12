package lk.ijse.online_appointment_platform.repo;

import lk.ijse.online_appointment_platform.dto.GigDetailsResponseDTO;
import lk.ijse.online_appointment_platform.entity.Gig_details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GigDetailsRepository extends JpaRepository<Gig_details, Long> {
    Optional<Gig_details> findById(Long gigId);
    List<Gig_details> findBySubCategoryId(Long subCategoryId);
    List<Gig_details> findByUserId(Long userId);

    @Query("SELECT new lk.ijse.online_appointment_platform.dto.GigDetailsResponseDTO(" +
            "g.id, g.FullName, g.image, g.degree, g.experience, g.amountCharge, " +
            "g.location, g.contactNumber, g.maxAppointmentsPerDay, g.active, g.dateTime, " +
            "g.user.id, g.category.name, g.subCategory.name) " +
            "FROM Gig_details g")
    List<GigDetailsResponseDTO> getAllGigDetailsWithNames();

}
