package com.example.productservice.exception;

import com.example.productservice.dto.Result;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final HttpStatus status;
    private final Result errorResult;

    public CustomException(HttpStatus status, String message) {
        this.status = status;
        this.errorResult = Result.createErrorResult(message);
    }
}
