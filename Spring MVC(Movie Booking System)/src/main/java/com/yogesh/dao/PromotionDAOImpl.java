package com.yogesh.dao;

import com.yogesh.dto.request.PromotionRequest;
import com.yogesh.dto.response.PromotionResponse;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public class PromotionDAOImpl implements PromotionDAO {

    @Override
    public PromotionResponse addPromotion(PromotionRequest request) {
        String sql = "INSERT INTO promotions (code, discount_percentage, start_date, end_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, request.getCode());
            pstmt.setDouble(2, request.getDiscountPercentage());
            pstmt.setDate(3, request.getStartDate());
            pstmt.setDate(4, request.getEndDate());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return getPromotionById(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PromotionResponse getPromotionById(int promotionId) {
        String sql = "SELECT * FROM promotions WHERE promotion_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, promotionId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapPromotionToResponse(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PromotionResponse getPromotionByCode(String code) {
        String sql = "SELECT * FROM promotions WHERE code = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapPromotionToResponse(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PromotionResponse> getAllPromotions() {
        List<PromotionResponse> promotions = new ArrayList<>();
        String sql = "SELECT * FROM promotions";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                promotions.add(mapPromotionToResponse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotions;
    }

    @Override
    public boolean updatePromotion(PromotionRequest request, int promotionId) {
        String sql = "UPDATE promotions SET code = ?, discount_percentage = ?, start_date = ?, end_date = ? WHERE promotion_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, request.getCode());
            pstmt.setDouble(2, request.getDiscountPercentage());
            pstmt.setDate(3, request.getStartDate());
            pstmt.setDate(4, request.getEndDate());
            pstmt.setInt(5, promotionId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deletePromotion(int promotionId) {
        String sql = "DELETE FROM promotions WHERE promotion_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, promotionId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to map ResultSet to PromotionResponse
    private PromotionResponse mapPromotionToResponse(ResultSet rs) throws SQLException {
        PromotionResponse response = new PromotionResponse();
        response.setPromotionId(rs.getInt("promotion_id"));
        response.setCode(rs.getString("code"));
        response.setDiscountPercentage(rs.getDouble("discount_percentage"));
        response.setStartDate(rs.getDate("start_date"));
        response.setEndDate(rs.getDate("end_date"));
        response.setActive(rs.getDate("end_date").after(new java.util.Date()));
        return response;
    }
}
