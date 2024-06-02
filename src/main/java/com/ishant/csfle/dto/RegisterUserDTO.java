package com.ishant.csfle.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDTO {
    private String name;
    private String username;
    private String email;
    private String mobile;
    private String password;
}
