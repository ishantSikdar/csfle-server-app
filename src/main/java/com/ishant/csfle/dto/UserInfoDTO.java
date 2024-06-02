package com.ishant.csfle.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfoDTO {
    private String name;
    private String username;
    private String email;
    private String mobile;
    private String cardNumber;
    private String cardCvv;
    private String cardExpiry;
}
