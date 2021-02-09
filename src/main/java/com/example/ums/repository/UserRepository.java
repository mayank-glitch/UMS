package com.example.ums.repository;

import com.example.ums.entity.cassandra.UserEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends CassandraRepository<UserEntity, UUID> {

    UserEntity findFirstByIdAndDeletedFalse(UUID id);

    Page<UserEntity> findAllByIdAndDeletedFalse(UUID id, Pageable page);

    UUID findTopIdByEmailAndDeletedFalse(String email);

    UUID findTopIdByPhoneNoAndDeletedFalse(String phoneNo);

    UUID findTopIdByUserNameAndDeletedFalse(String email);
}
