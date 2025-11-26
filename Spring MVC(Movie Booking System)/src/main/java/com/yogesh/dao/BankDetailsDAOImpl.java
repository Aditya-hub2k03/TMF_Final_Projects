package com.yogesh.dao;

import com.yogesh.dto.request.BankDetailsRequest;
import com.yogesh.dto.response.BankDetailsResponse;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
@Repository
public class BankDetailsDAOImpl implements BankDetailsDAO {

    @Override
    public BankDetailsResponse addBankDetails(BankDetailsRequest request) {
        String sql = "INSERT INTO bank_details (user_id, card_number, card_holder_name, expiry_date, cvv) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, request.getUserId());
            pstmt.setString(2, request.getCardNumber());
            pstmt.setString(3, request.getCardHolderName());
            pstmt.setString(4, request.getExpiryDate());
            pstmt.setString(5, request.getCvv());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return getBankDetailsById(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BankDetailsResponse getBankDetailsById(int bankId) {
        String sql = "SELECT * FROM bank_details WHERE bank_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bankId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapBankDetailsToResponse(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BankDetailsResponse getBankDetailsByUser(int userId) {
        String sql = "SELECT * FROM bank_details WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapBankDetailsToResponse(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateBankDetails(BankDetailsRequest request, int bankId) {
        String sql = "UPDATE bank_details SET card_number = ?, card_holder_name = ?, expiry_date = ?, cvv = ? WHERE bank_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, request.getCardNumber());
            pstmt.setString(2, request.getCardHolderName());
            pstmt.setString(3, request.getExpiryDate());
            pstmt.setString(4, request.getCvv());
            pstmt.setInt(5, bankId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteBankDetails(int bankId) {
        String sql = "DELETE FROM bank_details WHERE bank_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bankId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to map ResultSet to BankDetailsResponse
    private BankDetailsResponse mapBankDetailsToResponse(ResultSet rs) throws SQLException {
        BankDetailsResponse response = new BankDetailsResponse();
        response.setBankId(rs.getInt("bank_id"));
        response.setUserId(rs.getInt("user_id"));
        response.setCardHolderName(rs.getString("card_holder_name"));
        response.setExpiryDate(rs.getString("expiry_date"));
        response.setLastFourDigits(rs.getString("card_number").substring(rs.getString("card_number").length() - 4));
        return response;
    }
}

