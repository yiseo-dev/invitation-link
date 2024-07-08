package com.invitation.model;

import lombok.Data;
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
