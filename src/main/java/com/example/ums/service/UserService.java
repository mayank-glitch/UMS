package com.example.ums.service;

import com.example.ums.model.PageResponse;
import com.example.ums.model.UserHistoryResponse;
import com.example.ums.model.UserResponse;
import com.example.ums.model.UserRequest;

import java.util.UUID;

public interface UserService {

    UserResponse createUser(UserRequest request);

    void createUserUsingKafka(UserRequest request);

    UserResponse getUser(UUID userId);

    UserResponse updateUser(UUID userId, UserRequest request);

    void deleteUser(UUID userId);

    PageResponse<UserHistoryResponse> getUserHistory(UUID id, int pageNo, int pageSize);
}
