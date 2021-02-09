package com.example.ums.service;

import com.example.ums.model.UserResponse;
import com.example.ums.model.UserRequest;

import java.util.UUID;

public interface UserService {

    UserResponse createUser(UserRequest request);
    UserResponse getUser(UUID userId);
    UserResponse updateUser(UUID userId, UserRequest request);
    void deleteUser(UUID userId);
}
