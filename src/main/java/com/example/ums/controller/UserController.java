package com.example.ums.controller;

import com.example.ums.model.*;
import com.example.ums.service.UserService;
import com.example.ums.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseBean<UserResponse> createUser(@RequestBody UserRequest request){
        return new ResponseBean<>(userService.createUser(request));
    }

    @PostMapping(value = "v1")
    public ResponseBean<String> createUserUsingKafka(@RequestBody UserRequest request){
        userService.createUserUsingKafka(request);
        return new ResponseBean<>(Constants.OK);
    }

    @GetMapping(path = "/{userId}")
    public ResponseBean<UserResponse> getUser(@PathVariable(name = "userId") UUID userId){
        return new ResponseBean<>(userService.getUser(userId));
    }

    @PutMapping(path = "/{userId}")
    public ResponseBean<UserResponse> updateUser(@PathVariable(name = "userId") UUID userId,
                                                 @RequestBody UserRequest request){
        return new ResponseBean<>(userService.updateUser(userId, request));
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseBean<String> deleteUser(@PathVariable(name = "userId") UUID userId){
        userService.deleteUser(userId);
        return new ResponseBean<>(Constants.OK);
    }

    @GetMapping(path = "/{userId}/history")
    public PageResponse<UserHistoryResponse> getUserHistory(@PathVariable(name = "userId") UUID userId,
                                                            @RequestBody CustomPageRequest request){
        PageResponse<UserHistoryResponse> response = userService.getUserHistory(userId, request);
        return response;
    }
}
