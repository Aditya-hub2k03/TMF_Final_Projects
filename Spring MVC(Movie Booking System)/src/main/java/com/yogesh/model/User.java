package com.yogesh.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String username;
    private String email;
    private String passwordHash;
    private UserRole role;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public enum UserRole {
        NORMAL_USER, THEATRE_ADMIN, APPLICATION_ADMIN
    }
}
