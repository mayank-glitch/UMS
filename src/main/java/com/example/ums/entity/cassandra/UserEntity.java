package com.example.ums.entity.cassandra;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import javax.persistence.Table;
import java.util.UUID;

@Data
@Table(name = "user")
public class UserEntity extends BaseEntity {

    @PrimaryKeyColumn(value = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID id;

    @Column(value = "user_name")
    private String userName;

    @Column(value = "first_name")
    private String firstName;

    @Column(value = "last_name")
    private String lastName;

    @Column(value = "email")
    private String email;

    @Column(value = "phone_no")
    private String phoneNo;

    @Column(value = "password")
    private String password;

    @PrimaryKeyColumn(value = "deleted", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private boolean deleted = false;

    @PrimaryKeyColumn(value = "version", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Integer version;

}
