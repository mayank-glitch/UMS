package com.example.ums.repository;

import com.example.ums.entity.es.UserESEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface UserEsRepository extends ElasticsearchRepository<UserESEntity, UUID> {

    UserESEntity findByIdAndIsLatest(UUID id, boolean isLatest);
    UserESEntity findByEmailAndIsLatest(String email, boolean isLatest);
    UserESEntity findByPhoneNoAndIsLatest(String phoneNo, boolean isLatest);
    UserESEntity findByUserNameAndIsLatest(String userName, boolean isLatest);
    Page<UserESEntity> findById(UUID id, Pageable pageable);


//    UserESEntity findByUserNameOrEmailOrPhoneNoAndIsLatest(String userName, String email, String phoneNo, boolean isLatest);

}
