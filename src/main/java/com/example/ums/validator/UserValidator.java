package com.example.ums.validator;

import com.example.ums.entity.cassandra.UserEntity;
import com.example.ums.exception.UmsException;
import com.example.ums.model.UserRequest;
import com.example.ums.repository.UserRepository;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class UserValidator {

    @Autowired
    private UserRepository userRepository;

    public void validateCreateUserRequest(UserRequest request) {
        if (Objects.isNull(request)) {
            throw new UmsException(UmsException.UmsExceptionEnum.PAYLOAD_EMPTY);
        }
        if (StringUtils.isEmpty(request.getUsername())) {
            throw new UmsException(UmsException.UmsExceptionEnum.USER_NAME_EMPTY);
        }
        if (StringUtils.isEmpty(request.getPassword())) {
            throw new UmsException(UmsException.UmsExceptionEnum.PASSWORD_EMPTY);
        }
        if (StringUtils.isEmpty(request.getEmail())) {
            throw new UmsException(UmsException.UmsExceptionEnum.EMAIL_EMPTY);
        }
        if (StringUtils.isEmpty(request.getFirstName())) {
            throw new UmsException(UmsException.UmsExceptionEnum.FIRST_NAME_EMPTY);
        }
        if (StringUtils.isEmpty(request.getLastName())) {
            throw new UmsException(UmsException.UmsExceptionEnum.LAST_NAME_EMPTY);
        }
        if (StringUtils.isEmpty(request.getPhone())) {
            throw new UmsException(UmsException.UmsExceptionEnum.PHONE_NO_EMPTY);
        }
    }

    public void validateUserId(UUID id) {
        if (id == null) {
            throw new UmsException(UmsException.UmsExceptionEnum.USER_ID_EMPTY);
        }
    }

    public void validateUpdateUserRequest(UUID id, UserRequest userRequest) {
        if (id == null) {
            throw new UmsException(UmsException.UmsExceptionEnum.USER_ID_EMPTY);
        }
        if (Objects.isNull(userRequest)) {
            throw new UmsException(UmsException.UmsExceptionEnum.PAYLOAD_EMPTY);
        }
    }

    public void validateUpdateEmailRequest(UserEntity existingUser, @NonNull String email) {
        UUID id = userRepository.findTopIdByEmailAndDeletedFalse(email);
        if(id!=null && existingUser.getId() != id){
            throw new UmsException(UmsException.UmsExceptionEnum.EMAIL_ALREADY_EXIST);
        }
    }

    public void validateUpdateUserNameRequest(UserEntity existingUser, @NonNull String userName) {
        UUID id = userRepository.findTopIdByUserNameAndDeletedFalse(userName);
        if(id!=null && existingUser.getId() != id){
            throw new UmsException(UmsException.UmsExceptionEnum.EMAIL_ALREADY_EXIST);
        }
    }

    public void validateUpdatePhoneNoRequest(UserEntity existingUser, @NonNull String phoneNo) {
        UUID id = userRepository.findTopIdByPhoneNoAndDeletedFalse(phoneNo);
        if(id!=null && existingUser.getId() != id){
            throw new UmsException(UmsException.UmsExceptionEnum.EMAIL_ALREADY_EXIST);
        }
    }
}
