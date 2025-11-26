package com.yogesh.service;

import com.yogesh.dto.request.UserRegistrationRequest;
import com.yogesh.dto.request.UserLoginRequest;
import com.yogesh.dto.request.UpdatePasswordRequest;
import com.yogesh.dto.response.UserResponse;
import java.util.List;

public interface UserService {
    UserResponse registerUser(UserRegistrationRequest request);
    UserResponse loginUser(UserLoginRequest request);
    boolean updatePassword(UpdatePasswordRequest request);
    UserResponse getUserById(int userId);
    List<UserResponse> getAllUsers();
    boolean updateUserRole(int userId, String role);
}
