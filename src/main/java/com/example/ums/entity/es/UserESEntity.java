package com.example.ums.entity.es;

import org.springframework.data.elasticsearch.annotations.Document;

import java.util.UUID;

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

}
