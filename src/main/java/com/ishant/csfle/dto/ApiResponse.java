package com.ishant.csfle.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ishant.csfle.util.IDUtil;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
    private long requestId;
    private String message;
    private Date timestamp;
    private T data;

    @JsonIgnore
    private HttpStatus httpStatus;

    public ApiResponse() {
        this.requestId = IDUtil.generateApiId();
        this.message = "";
        this.timestamp = Date.from(Instant.now());
        this.httpStatus = HttpStatus.PROCESSING;
        this.data = null;
    }
}
