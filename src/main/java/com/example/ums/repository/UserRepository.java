package com.example.ums.repository;

import com.example.ums.entity.cassandra.UserEntity;
import com.example.ums.entity.es.UserESEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends CassandraRepository<UserEntity, UUID> {

    UserEntity findFirstById(UUID id);

    Slice<UserEntity> findById(UUID id, Pageable pageable);

}
