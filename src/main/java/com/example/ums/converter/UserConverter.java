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

    public static UserHistoryResponse convert(UserESEntity userESEntity){
        UserHistoryResponse userHistoryResponse = new UserHistoryResponse();
        if(userESEntity != null){
            userHistoryResponse.setUserBaseResponse(convertES(userESEntity));
            userHistoryResponse.setVersion(userESEntity.getVersion());
        }
        return userHistoryResponse;
    }

    public static List<UserHistoryResponse> convert(List<UserESEntity> users){
        List<UserHistoryResponse> userHistoryResponses = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(users)){
            users.forEach(userESEntity -> userHistoryResponses.add(convert(userESEntity)));
        }
        return userHistoryResponses;
    }


}
