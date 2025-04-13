package lk.ijse.online_appointment_platform.controller;

import lk.ijse.online_appointment_platform.dto.ReviewDTO;
import lk.ijse.online_appointment_platform.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<String> saveReview(@RequestBody ReviewDTO dto) {
        reviewService.saveReview(dto);
        return ResponseEntity.ok("Review added successfully!");
    }
}
