package com.invitation.exception;

import com.invitation.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customException(CustomException ce) {
        return ResponseEntity
                .status(ce.getError().getHttpStatus())
                .body(new ErrorResponse(ce.getError().getHttpStatus(),ce.getError().getErrorMsg()));
    }

}
