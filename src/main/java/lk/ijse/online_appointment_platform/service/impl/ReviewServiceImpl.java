package lk.ijse.online_appointment_platform.service.impl;

import lk.ijse.online_appointment_platform.dto.ReviewDTO;
import lk.ijse.online_appointment_platform.entity.Gig_details;
import lk.ijse.online_appointment_platform.entity.Review;
import lk.ijse.online_appointment_platform.entity.User;
import lk.ijse.online_appointment_platform.repo.GigDetailsRepository;
import lk.ijse.online_appointment_platform.repo.ReviewRepository;
import lk.ijse.online_appointment_platform.repo.UserRepository;
import lk.ijse.online_appointment_platform.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GigDetailsRepository gigDetailsRepository;

    @Override
    public void saveReview(ReviewDTO dto) {
        User user = userRepository.findById(String.valueOf(dto.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found"));
        Gig_details gig = gigDetailsRepository.findById(dto.getGigId())
                .orElseThrow(() -> new RuntimeException("Gig not found"));

        // 📝 Create and save review
        Review review = new Review();
        review.setUser(user);
        review.setGig(gig);
        reviewRepository.save(review);

        // ⭐ Increase gig's star count by 1
        gig.setStar(gig.getStar() + 1);
        gigDetailsRepository.save(gig);
    }
}
