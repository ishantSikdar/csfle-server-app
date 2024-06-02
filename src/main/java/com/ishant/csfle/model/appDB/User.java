package com.ishant.csfle.model.appDB;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Builder
@Document(collection = "user")
public class User {

    @Id
    private String id;
    private String username;
    private String email;
    private String mobile;
    private String password;
    private String pwdHash;
    private String cardNumber;
    private String cvv;
    private Instant lastLogin;
    private Instant createdAt;
    private Instant updatedAt;
}
