package com.example.ums.entity.es;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.UUID;

@Data
@Document(indexName = "user")
public class UserESEntity {

    private UUID id;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNo;

    private String password;

    private boolean deleted;

    private Integer version;

    private boolean isLatest;

    private Long createdAt;

    private Long updatedAt;
}
