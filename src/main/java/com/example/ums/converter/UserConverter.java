package com.example.ums.converter;

import com.example.ums.entity.cassandra.UserEntity;
import com.example.ums.model.UserResponse;

public class UserConverter {

    private UserConverter(){}

    public static UserResponse convert(UserEntity user){
        UserResponse userResponse = new UserResponse();
        if(user != null){
            userResponse.setId(user.getId());
            userResponse.setEmail(user.getEmail());
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            userResponse.setUsername(user.getUserName());
            userResponse.setId(user.getId());
            userResponse.setPassword(user.getPassword());
            userResponse.setPhone(user.getPhoneNo());
        }
        return userResponse;
    }
}
