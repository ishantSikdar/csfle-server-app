package com.ishant.csfle.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserLoginResponse {
    private String accessToken;
    private String username;
}
