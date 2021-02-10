package com.example.ums.service.impl;

import com.example.ums.converter.UserConverter;
import com.example.ums.entity.cassandra.UserEntity;
import com.example.ums.entity.es.UserESEntity;
import com.example.ums.exception.UmsException;
import com.example.ums.model.PageResponse;
import com.example.ums.model.UserHistoryResponse;
import com.example.ums.model.UserResponse;
import com.example.ums.model.UserRequest;
import com.example.ums.repository.UserEsRepository;
import com.example.ums.repository.UserRepository;
import com.example.ums.service.UserService;
import com.example.ums.service.kafka.KafkaProducerService;
import com.example.ums.util.DateUtil;
import com.example.ums.validator.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserEsRepository userEsRepository;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Transactional
    @Override
    public UserResponse createUser(UserRequest request) {
        userValidator.validateCreateUserRequest(request);
        userValidator.validateDuplicateUser(request);
        UUID id = UUID.randomUUID();
        Long createdAt = DateUtil.currentTimeSec();
        Long updatedAt = DateUtil.currentTimeSec();
        UserEntity cassandraUserEntity = saveInCassandra(request, id, createdAt, updatedAt);
        UserESEntity esUserEntity = saveInES(request, id, createdAt, updatedAt);
        return UserConverter.convert(cassandraUserEntity);
    }

    private UserEntity saveInCassandra(UserRequest request, UUID uuid, Long createdAt, Long updatedAt){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(uuid);
        userEntity.setEmail(request.getEmail());
        userEntity.setUserName(request.getUserName());
        userEntity.setFirstName(request.getFirstName());
        userEntity.setLastName(request.getLastName());
        userEntity.setPassword(request.getPassword());
        userEntity.setPhoneNo(request.getPhone());
        userEntity.setCreatedAt(createdAt);
        userEntity.setUpdatedAt(updatedAt);
        userEntity.setVersion(0);
        userEntity.setLatest(true);
        return userRepository.save(userEntity);
    }

    private UserESEntity saveInES(UserRequest request, UUID uuid, Long createdAt, Long updatedAt){
        UserESEntity userESEntity = new UserESEntity();
        userESEntity.setId(uuid);
        userESEntity.setEmail(request.getEmail());
        userESEntity.setUserName(request.getUserName());
        userESEntity.setFirstName(request.getFirstName());
        userESEntity.setLastName(request.getLastName());
        userESEntity.setPassword(request.getPassword());
        userESEntity.setPhoneNo(request.getPhone());
        userESEntity.setCreatedAt(createdAt);
        userESEntity.setUpdatedAt(updatedAt);
        userESEntity.setVersion(0);
        userESEntity.setLatest(true);
        return userEsRepository.save(userESEntity);
    }

    @Override
    public void createUserUsingKafka(UserRequest request) {
        kafkaProducerService.sendCreateUserRequestToKafka(request);
    }

    @Override
    public UserResponse getUser(UUID userId) {
        userValidator.validateUserId(userId);
        UserEntity user = userRepository.findFirstById(userId);
        if (user == null) {
            throw new UmsException(UmsException.UmsExceptionEnum.USER_NOT_FOUND);
        }
        return UserConverter.convert(user);
    }

    @Transactional
    @Override
    public UserResponse updateUser(UUID id, UserRequest request) {
        userValidator.validateUpdateUserRequest(id, request);
        UserEntity existingUserCassandra = userRepository.findFirstById(id);
        UserESEntity existingUserEs = userEsRepository.findByIdAndIsLatest(id, true);
        if(existingUserCassandra == null || existingUserEs == null){
            throw new UmsException(UmsException.UmsExceptionEnum.USER_NOT_FOUND);
        }
       return updateReleventFields(existingUserCassandra, existingUserEs, request);
    }

    private UserResponse updateReleventFields(UserEntity existingUserCassandra, UserESEntity existingUserEs, UserRequest request) {
        UserEntity newUserCassandra = getNewUser(existingUserCassandra);
        UserESEntity newUserEs = getNewUser(existingUserEs);
        boolean isAnyChange = false;

        isAnyChange = updateEmail(existingUserCassandra, existingUserEs, newUserCassandra, newUserEs, request) || isAnyChange;
        isAnyChange = updatePhone(existingUserCassandra, existingUserEs, newUserCassandra, newUserEs, request) || isAnyChange;
        isAnyChange = updateUserName(existingUserCassandra, existingUserEs, newUserCassandra, newUserEs, request) || isAnyChange;
        isAnyChange = updateFirstName(existingUserCassandra, existingUserEs, newUserCassandra, newUserEs, request) || isAnyChange;
        isAnyChange = updateLastName(existingUserCassandra, existingUserEs, newUserCassandra, newUserEs, request) || isAnyChange;
        isAnyChange = updatePassword(existingUserCassandra, existingUserEs, newUserCassandra, newUserEs, request) || isAnyChange;

        if (isAnyChange){
            existingUserCassandra.setLatest(false);
            existingUserEs.setLatest(false);
            newUserCassandra = userRepository.save(newUserCassandra);
            userRepository.save(existingUserCassandra); // keeping both version in Cassandra
            userEsRepository.save(newUserEs);  //keeping only latest version in Elastic search
            return UserConverter.convert(newUserCassandra);
        }
        throw new UmsException(UmsException.UmsExceptionEnum.NOTHING_TO_UPDATE);
    }

    private UserEntity getNewUser(UserEntity existingUser){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(existingUser.getUserName());
        userEntity.setEmail(existingUser.getEmail());
        userEntity.setPhoneNo(existingUser.getPhoneNo());
        userEntity.setFirstName(existingUser.getFirstName());
        userEntity.setLastName(existingUser.getLastName());
        userEntity.setPassword(existingUser.getPassword());
        userEntity.setId(existingUser.getId());
        userEntity.setCreatedAt(DateUtil.currentTimeSec());
        userEntity.setUpdatedAt(DateUtil.currentTimeSec());
        userEntity.setVersion(existingUser.getVersion() + 1);
        userEntity.setLatest(true);
        return userEntity;
    }

    private UserESEntity getNewUser(UserESEntity existingUser){
        UserESEntity userESEntity = new UserESEntity();
        userESEntity.setUserName(existingUser.getUserName());
        userESEntity.setEmail(existingUser.getEmail());
        userESEntity.setPhoneNo(existingUser.getPhoneNo());
        userESEntity.setFirstName(existingUser.getFirstName());
        userESEntity.setLastName(existingUser.getLastName());
        userESEntity.setPassword(existingUser.getPassword());
        userESEntity.setId(existingUser.getId());
        userESEntity.setCreatedAt(DateUtil.currentTimeSec());
        userESEntity.setUpdatedAt(DateUtil.currentTimeSec());
        userESEntity.setVersion(existingUser.getVersion() + 1);
        userESEntity.setLatest(true);
        return userESEntity;
    }

    private boolean updateEmail(UserEntity existingUserCassandra,
                                UserESEntity existingUserEs,
                                UserEntity newUserCassandra,
                                UserESEntity newUserEs,
                                UserRequest request) {
        if (StringUtils.isNotBlank(request.getEmail()) && !request.getEmail().equals(existingUserCassandra.getEmail())) {
            userValidator.validateUpdateEmailRequest(existingUserCassandra, request.getEmail());
            newUserCassandra.setEmail(request.getEmail());
            newUserEs.setEmail(request.getEmail());
            return true;
        }
        return false;
    }

    private boolean updateUserName(UserEntity existingUserCassandra,
                                   UserESEntity existingUserEs,
                                   UserEntity newUserCassandra,
                                   UserESEntity newUserEs,
                                   UserRequest request) {
        if (StringUtils.isNotBlank(request.getUserName()) && !request.getUserName().equals(existingUserCassandra.getUserName())) {
            userValidator.validateUpdateUserNameRequest(existingUserCassandra, request.getUserName());
            newUserCassandra.setUserName(request.getUserName());
            newUserEs.setUserName(request.getUserName());
            return true;
        }
        return false;
    }

    private boolean updatePhone(UserEntity existingUserCassandra,
                                UserESEntity existingUserEs,
                                UserEntity newUserCassandra,
                                UserESEntity newUserEs,
                                UserRequest request) {
        if (StringUtils.isNotBlank(request.getPhone()) && !request.getPhone().equals(existingUserCassandra.getPhoneNo())) {
            userValidator.validateUpdatePhoneNoRequest(existingUserCassandra, request.getPhone());
            newUserCassandra.setPhoneNo(request.getPhone());
            newUserEs.setPhoneNo(request.getPhone());
            return true;
        }
        return false;
    }

    private boolean updateFirstName(UserEntity existingUserCassandra,
                                    UserESEntity existingUserEs,
                                    UserEntity newUserCassandra,
                                    UserESEntity newUserEs,
                                    UserRequest request) {
        if (StringUtils.isNotBlank(request.getFirstName()) && !request.getFirstName().equals(existingUserCassandra.getFirstName())) {
            newUserCassandra.setFirstName(request.getFirstName());
            newUserEs.setFirstName(request.getFirstName());
            return true;
        }
        return false;
    }

    private boolean updateLastName(UserEntity existingUserCassandra,
                                   UserESEntity existingUserEs,
                                   UserEntity newUserCassandra,
                                   UserESEntity newUserEs,
                                   UserRequest request) {
        if (StringUtils.isNotBlank(request.getLastName()) && !request.getLastName().equals(existingUserCassandra.getLastName())) {
            newUserCassandra.setLastName(request.getLastName());
            newUserEs.setLastName(request.getLastName());
            return true;
        }
        return false;
    }

    private boolean updatePassword(UserEntity existingUserCassandra,
                                   UserESEntity existingUserEs,
                                   UserEntity newUserCassandra,
                                   UserESEntity newUserEs,
                                   UserRequest request) {
        if (StringUtils.isNotBlank(request.getPassword()) && !request.getPassword().equals(existingUserCassandra.getPassword())) {
            newUserCassandra.setPassword(request.getPassword());
            newUserEs.setPassword(request.getPassword());
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public void deleteUser(UUID id) {
        userValidator.validateUserId(id);
        userRepository.deleteById(id);
        userEsRepository.deleteById(id);
    }

    @Override
    public PageResponse<UserHistoryResponse> getUserHistory(UUID id, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UserESEntity> userPage = userEsRepository.findById(id, pageable);

        List<UserHistoryResponse> userHistoryResponses = UserConverter.convert(userPage.getContent());

        PageResponse<UserHistoryResponse> pageResponse = new PageResponse<>();
        pageResponse.pageNo(userPage.getNumber())
                .totalPages(userPage.getTotalPages())
                .totalCount(userPage.getTotalElements())
                .list(userHistoryResponses)
                .pageSize(pageResponse.getList().size());

        return pageResponse;
    }
}
