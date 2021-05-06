package com.example.ums.service;

import com.example.ums.model.*;

import java.util.UUID;

public interface UserService {

    UserResponse createUser(UserRequest request);

    void createUserUsingKafka(UserRequest request);

    UserResponse getUser(UUID userId);

    UserResponse updateUser(UUID userId, UserRequest request);

    void deleteUser(UUID userId);

    PageResponse<UserHistoryResponse> getUserHistory(UUID id, CustomPageRequest request);
}
