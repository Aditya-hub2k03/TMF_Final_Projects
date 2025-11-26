package com.yogesh.service;

import com.yogesh.dto.request.ReviewRequest;
import com.yogesh.dto.response.ReviewResponse;
import java.util.List;

public interface ReviewService {
    ReviewResponse addReview(ReviewRequest request);
    ReviewResponse getReviewById(int reviewId);
    List<ReviewResponse> getReviewsByMovie(int movieId);
    List<ReviewResponse> getReviewsByUser(int userId);
    boolean deleteReview(int reviewId);
}
