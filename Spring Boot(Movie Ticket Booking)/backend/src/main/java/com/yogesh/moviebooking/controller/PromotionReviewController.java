package com.yogesh.moviebooking.controller;

import com.yogesh.moviebooking.model.*;
import com.yogesh.moviebooking.repository.*;
import com.yogesh.moviebooking.security.AppUserDetails;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class PromotionReviewController {

    private final PromotionRepository promotionRepository;
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    public PromotionReviewController(PromotionRepository promotionRepository,
                                     ReviewRepository reviewRepository,
                                     MovieRepository movieRepository) {
        this.promotionRepository = promotionRepository;
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
    }

    @PostMapping("/promotions/apply")
    public ResponseEntity<?> applyPromotion(@RequestBody Map<String, String> body) {
        String code = body.get("code");
        if (code == null || code.isBlank()) {
            return ResponseEntity.badRequest().body("code is required");
        }
        return promotionRepository.findByCode(code)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Invalid promotion code"));
    }

    @PostMapping("/movies/{movieId}/reviews")
    public ResponseEntity<?> addReview(@AuthenticationPrincipal AppUserDetails principal,
                                       @PathVariable Long movieId,
                                       @RequestBody Map<String, Object> body) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        Number ratingNum = (Number) body.get("rating");
        String comment = (String) body.get("comment");
        if (ratingNum == null) {
            return ResponseEntity.badRequest().body("rating is required");
        }
        int rating = ratingNum.intValue();
        if (rating < 1 || rating > 5) {
            return ResponseEntity.badRequest().body("rating must be between 1 and 5");
        }
        Review review = Review.builder()
                .movie(movie)
                .user(principal.getDomainUser())
                .rating(rating)
                .comment(comment)
                .build();
        reviewRepository.save(review);
        return ResponseEntity.ok(review);
    }
}