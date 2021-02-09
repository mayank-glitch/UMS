package com.example.ums.entity.cassandra;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;

import java.util.UUID;

@Data
public class BaseEntity {

    @Column(value = "created_at")
    private Long created_at;

    @Column(value = "updated_at")
    private Long updated_at;

}
