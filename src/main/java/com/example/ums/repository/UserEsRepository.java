package com.example.ums.repository;

import com.example.ums.entity.es.UserESEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface UserEsRepository extends ElasticsearchRepository<UserESEntity, UUID> {

    UserESEntity
}
