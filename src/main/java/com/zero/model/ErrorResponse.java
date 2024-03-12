package com.zero.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {
    private HttpStatus status;
    private String message;

    public ErrorResponse(HttpStatus httpStatus, String errorMsg) {
        this.status = httpStatus;
        this.message = errorMsg;
    }
}
