package com.yogesh.service;

import com.yogesh.dao.ReviewDAO;
import com.yogesh.dto.request.ReviewRequest;
import com.yogesh.dto.response.ReviewResponse;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDAO reviewDAO;

    public ReviewServiceImpl(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    @Override
    public ReviewResponse addReview(ReviewRequest request) {
        // Validate request
        if (request.getUserId() <= 0 || request.getMovieId() <= 0 || request.getRating() <= 0 || request.getRating() > 5) {
            throw new IllegalArgumentException("User ID, movie ID, and valid rating (1-5) are required.");
        }
        return reviewDAO.addReview(request);
    }

    @Override
    public ReviewResponse getReviewById(int reviewId) {
        if (reviewId <= 0) {
            throw new IllegalArgumentException("Invalid review ID.");
        }
        return reviewDAO.getReviewById(reviewId);
    }

    @Override
    public List<ReviewResponse> getReviewsByMovie(int movieId) {
        if (movieId <= 0) {
            throw new IllegalArgumentException("Invalid movie ID.");
        }
        return reviewDAO.getReviewsByMovie(movieId);
    }

    @Override
    public List<ReviewResponse> getReviewsByUser(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID.");
        }
        return reviewDAO.getReviewsByUser(userId);
    }

    @Override
    public boolean deleteReview(int reviewId) {
        if (reviewId <= 0) {
            throw new IllegalArgumentException("Invalid review ID.");
        }
        return reviewDAO.deleteReview(reviewId);
    }
}
