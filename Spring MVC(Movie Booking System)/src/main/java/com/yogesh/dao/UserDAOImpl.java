package com.yogesh.dao;

import com.yogesh.dto.request.UserRegistrationRequest;
import com.yogesh.dto.request.UserLoginRequest;
import com.yogesh.dto.request.UpdatePasswordRequest;
import com.yogesh.dto.response.UserResponse;
import com.yogesh.model.User;
import com.yogesh.util.DBUtil;
import com.yogesh.util.PassUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public class UserDAOImpl implements UserDAO {

    @Override
    public UserResponse registerUser(UserRegistrationRequest request) {
        String sql = "INSERT INTO users (username, email, password_hash, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, request.getUsername());
            pstmt.setString(2, request.getEmail());
            pstmt.setString(3, PassUtil.hashPassword(request.getPassword()));
            pstmt.setString(4, request.getRole());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return getUserById(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserResponse loginUser(UserLoginRequest request) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, request.getEmail());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                if (PassUtil.verifyPassword(request.getPassword(), storedHash)) {
                    return mapUserToResponse(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updatePassword(UpdatePasswordRequest request) {
        String sql = "UPDATE users SET password_hash = ? WHERE user_id = ? AND password_hash = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String oldHash = getPasswordHash(request.getUserId());
            if (PassUtil.verifyPassword(request.getOldPassword(), oldHash)) {
                pstmt.setString(1, PassUtil.hashPassword(request.getNewPassword()));
                pstmt.setInt(2, request.getUserId());
                pstmt.setString(3, oldHash);
                return pstmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public UserResponse getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapUserToResponse(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<UserResponse> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapUserToResponse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean updateUserRole(int userId, String role) {
        String sql = "UPDATE users SET role = ? WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, role);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to map ResultSet to UserResponse
    private UserResponse mapUserToResponse(ResultSet rs) throws SQLException {
        UserResponse response = new UserResponse();
        response.setUserId(rs.getInt("user_id"));
        response.setUsername(rs.getString("username"));
        response.setEmail(rs.getString("email"));
        response.setRole(User.UserRole.valueOf(rs.getString("role")));
        return response;
    }

    // Helper method to get password hash
    private String getPasswordHash(int userId) {
        String sql = "SELECT password_hash FROM users WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password_hash");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
