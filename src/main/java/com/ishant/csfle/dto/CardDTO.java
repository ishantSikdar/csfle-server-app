package com.ishant.csfle.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDTO {
    private String cardNumber;
    private String cvv;
    private String expiry;
}
