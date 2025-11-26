package com.yogesh.dao;

import com.yogesh.dto.request.ReviewRequest;
import com.yogesh.dto.response.ReviewResponse;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public class ReviewDAOImpl implements ReviewDAO {

    @Override
    public ReviewResponse addReview(ReviewRequest request) {
        String sql = "INSERT INTO reviews (user_id, movie_id, rating, comment) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, request.getUserId());
            pstmt.setInt(2, request.getMovieId());
            pstmt.setInt(3, request.getRating());
            pstmt.setString(4, request.getComment());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return getReviewById(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ReviewResponse getReviewById(int reviewId) {
        String sql = "SELECT r.*, u.username FROM reviews r JOIN users u ON r.user_id = u.user_id WHERE review_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reviewId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapReviewToResponse(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ReviewResponse> getReviewsByMovie(int movieId) {
        List<ReviewResponse> reviews = new ArrayList<>();
        String sql = "SELECT r.*, u.username FROM reviews r JOIN users u ON r.user_id = u.user_id WHERE movie_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, movieId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                reviews.add(mapReviewToResponse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    @Override
    public List<ReviewResponse> getReviewsByUser(int userId) {
        List<ReviewResponse> reviews = new ArrayList<>();
        String sql = "SELECT r.*, u.username FROM reviews r JOIN users u ON r.user_id = u.user_id WHERE r.user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                reviews.add(mapReviewToResponse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    @Override
    public boolean deleteReview(int reviewId) {
        String sql = "DELETE FROM reviews WHERE review_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reviewId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to map ResultSet to ReviewResponse
    private ReviewResponse mapReviewToResponse(ResultSet rs) throws SQLException {
        ReviewResponse response = new ReviewResponse();
        response.setReviewId(rs.getInt("review_id"));
        response.setUserId(rs.getInt("user_id"));
        response.setUsername(rs.getString("username"));
        response.setMovieId(rs.getInt("movie_id"));
        response.setRating(rs.getInt("rating"));
        response.setComment(rs.getString("comment"));
        response.setCreatedAt(rs.getTimestamp("created_at"));
        return response;
    }
}
