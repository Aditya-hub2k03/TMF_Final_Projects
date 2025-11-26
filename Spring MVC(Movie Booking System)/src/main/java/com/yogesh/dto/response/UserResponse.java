package com.yogesh.dto.response;

import com.yogesh.model.User.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private int userId;
    private String username;
    private String email;
    private UserRole role;
}
