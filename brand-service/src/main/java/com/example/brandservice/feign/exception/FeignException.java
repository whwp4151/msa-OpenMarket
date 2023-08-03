package com.example.brandservice.feign.exception;

import com.example.brandservice.exception.CustomException;
import org.springframework.http.HttpStatus;

public class FeignException extends CustomException {

    public FeignException(HttpStatus status, String message) {
        super(status, message);
    }

}
