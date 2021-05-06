package com.example.ums.converter;

import com.example.ums.entity.cassandra.UserEntity;
import com.example.ums.entity.es.UserESEntity;
import com.example.ums.model.UserHistoryResponse;
import com.example.ums.model.UserResponse;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
            userResponse.setPassword(user.getPassword());
            userResponse.setPhone(user.getPhoneNo());
        }
        return userResponse;
    }

    public static UserResponse convertES(UserESEntity userESEntity){
        UserResponse userResponse = new UserResponse();
        if(userESEntity != null){
            userResponse.setId(userESEntity.getId());
            userResponse.setUsername(userESEntity.getUserName());
            userResponse.setEmail(userESEntity.getEmail());
            userResponse.setPassword(userESEntity.getPassword());
            userResponse.setPhone(userESEntity.getPhoneNo());
            userResponse.setFirstName(userESEntity.getFirstName());
            userResponse.setLastName(userESEntity.getLastName());
        }
        return userResponse;
    }

    public static UserHistoryResponse convertESToHistoryResponse(UserESEntity userESEntity){
        UserHistoryResponse userHistoryResponse = new UserHistoryResponse();
        if(userESEntity != null){
            userHistoryResponse.setUserResponse(convertES(userESEntity));
            userHistoryResponse.setVersion(userESEntity.getVersion());
        }
        return userHistoryResponse;
    }

    public static UserHistoryResponse convertToHistoryResponse(UserEntity userEntity){
        UserHistoryResponse userHistoryResponse = new UserHistoryResponse();
        if(userEntity != null){
            userHistoryResponse.setUserResponse(convert(userEntity));
            userHistoryResponse.setVersion(userEntity.getVersion());
        }
        return userHistoryResponse;
    }

    public static List<UserHistoryResponse> convertListESToHistoryResponse(List<UserESEntity> users){
        List<UserHistoryResponse> userHistoryResponses = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(users)){
            users.forEach(userESEntity -> userHistoryResponses.add(convertESToHistoryResponse(userESEntity)));
        }
        return userHistoryResponses;
    }

    public static List<UserHistoryResponse> convertListToHistoryResponse(List<UserEntity> users){
        List<UserHistoryResponse> userHistoryResponses = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(users)){
            users.forEach(userEntity -> userHistoryResponses.add(convertToHistoryResponse(userEntity)));
        }
        return userHistoryResponses;
    }


}
