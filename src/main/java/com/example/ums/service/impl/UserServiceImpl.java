package com.example.ums.service.impl;

import com.datastax.driver.core.utils.UUIDs;
import com.example.ums.converter.UserConverter;
import com.example.ums.entity.cassandra.UserEntity;
import com.example.ums.exception.UmsException;
import com.example.ums.model.UserResponse;
import com.example.ums.model.UserRequest;
import com.example.ums.repository.UserRepository;
import com.example.ums.service.UserService;
import com.example.ums.util.DateUtil;
import com.example.ums.validator.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserValidator userValidator;

    @Override
    public UserResponse createUser(UserRequest request) {
        userValidator.validateCreateUserRequest(request);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUIDs.random());
        userEntity.setEmail(request.getEmail());
        userEntity.setUserName(request.getUsername());
        userEntity.setFirstName(request.getFirstName());
        userEntity.setLastName(request.getLastName());
        userEntity.setPassword(request.getPassword());
        userEntity.setPhoneNo(request.getPhone());
        userEntity.setCreated_at(DateUtil.currentTimeSec());
        userEntity.setUpdated_at(DateUtil.currentTimeSec());
        userEntity.setVersion(0);
        userEntity = userRepository.save(userEntity);
        return UserConverter.convert(userEntity);
    }

    @Override
    public UserResponse getUser(UUID userId) {
        userValidator.validateUserId(userId);
        UserEntity user = userRepository.findFirstByIdAndDeletedFalse(userId);
        if (user == null) {
            throw new UmsException(UmsException.UmsExceptionEnum.USER_NOT_FOUND);
        }
        return UserConverter.convert(user);
    }

    @Override
    public UserResponse updateUser(UUID id, UserRequest request) {
        userValidator.validateUpdateUserRequest(id, request);
        UserEntity existingUser = userRepository.findFirstByIdAndDeletedFalse(id);

        UserEntity newUser = updateReleventFields(existingUser, request);

        if (newUser == null) {
            throw new UmsException(UmsException.UmsExceptionEnum.NOTHING_TO_UPDATE);
        }
        newUser = userRepository.save(newUser);
        return UserConverter.convert(newUser);
    }

    private UserEntity updateReleventFields(UserEntity existingUser, UserRequest request) {
        UserEntity newUser = new UserEntity();
        boolean isAnyChange = false;

        isAnyChange = updateEmail(existingUser, newUser, request) || isAnyChange;
        isAnyChange = updatePhone(existingUser, newUser, request) || isAnyChange;
        isAnyChange = updateUserName(existingUser, newUser, request) || isAnyChange;
        isAnyChange = updateFirstName(existingUser, newUser, request) || isAnyChange;
        isAnyChange = updateLastName(existingUser, newUser, request) || isAnyChange;
        isAnyChange = updatePassword(existingUser, newUser, request) || isAnyChange;

        newUser.setId(existingUser.getId());
        newUser.setCreated_at(existingUser.getCreated_at());
        newUser.setUpdated_at(DateUtil.currentTimeSec());
        newUser.setVersion(existingUser.getVersion() + 1);

        if (isAnyChange) return newUser;
        else return null;
    }

    private boolean updateEmail(UserEntity existingUser, UserEntity newUser, UserRequest request) {
        if (StringUtils.isNotBlank(request.getEmail())) {
            newUser.setEmail(request.getEmail());
            if (!request.getEmail().equals(existingUser.getEmail())) {
                userValidator.validateUpdateEmailRequest(existingUser, request.getEmail());
                return true;
            }
        }
        return false;
    }

    private boolean updateUserName(UserEntity existingUser, UserEntity newUser, UserRequest request) {
        if (StringUtils.isNotBlank(request.getUsername())) {
            newUser.setUserName(request.getUsername());
            if (!request.getUsername().equals(existingUser.getUserName())) {
                userValidator.validateUpdateUserNameRequest(existingUser, request.getUsername());
                return true;
            }
        }
        return false;
    }

    private boolean updatePhone(UserEntity existingUser, UserEntity newUser, UserRequest request) {
        if (StringUtils.isNotBlank(request.getPhone())) {
            newUser.setPhoneNo(request.getPhone());
            if (!request.getPhone().equals(existingUser.getPhoneNo())) {
                userValidator.validateUpdatePhoneNoRequest(existingUser, request.getPhone());
                return true;
            }
        }
        return false;
    }

    private boolean updateFirstName(UserEntity existingUser, UserEntity newUser, UserRequest request) {
        if (StringUtils.isNotBlank(request.getFirstName())) {
            newUser.setFirstName(request.getFirstName());
            return !request.getFirstName().equals(existingUser.getFirstName());
        }
        return false;
    }

    private boolean updateLastName(UserEntity existingUser, UserEntity newUser, UserRequest request) {
        if (StringUtils.isNotBlank(request.getLastName())) {
            newUser.setLastName(request.getLastName());
            return !request.getLastName().equals(existingUser.getLastName());
        }
        return false;
    }

    private boolean updatePassword(UserEntity existingUser, UserEntity newUser, UserRequest request) {
        if (StringUtils.isNotBlank(request.getPassword())) {
            newUser.setPassword(request.getPassword());
            return !request.getPassword().equals(existingUser.getPassword());
        }
        return false;
    }

    @Override
    public void deleteUser(UUID id) {
        userValidator.validateUserId(id);
        userRepository.deleteById(id);
    }

//    @Override
//    public UserHistoryPageResponse getUserHistory(UUID id, int pageNo, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNo, pageSize);
//        Page<UserEntity> userPage = userRepository.findAllByIdAndDeletedFalse(id, pageable);
//
//        return null;
//    }
}
