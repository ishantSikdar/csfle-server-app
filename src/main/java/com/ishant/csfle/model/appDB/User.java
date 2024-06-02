package com.ishant.csfle.model.appDB;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Builder
@Document(collection = "user")
public class User implements UserDetails {

    @Id
    private String id;
    private String name;
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

    @Override
    public String getPassword() {
        return this.getPwdHash();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
