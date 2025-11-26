package com.yogesh.dao;

import com.yogesh.dto.request.UserRegistrationRequest;
import com.yogesh.dto.request.UserLoginRequest;
import com.yogesh.dto.request.UpdatePasswordRequest;
import com.yogesh.dto.response.UserResponse;
import java.util.List;

public interface UserDAO {
    UserResponse registerUser(UserRegistrationRequest request);
    UserResponse loginUser(UserLoginRequest request);
    boolean updatePassword(UpdatePasswordRequest request);
    UserResponse getUserById(int userId);
    List<UserResponse> getAllUsers();
    boolean updateUserRole(int userId, String role);
}
