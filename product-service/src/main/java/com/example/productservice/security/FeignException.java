package com.example.productservice.security;

import com.example.productservice.security.CustomException;
import org.springframework.http.HttpStatus;

public class FeignException extends CustomException {

    public FeignException(HttpStatus status, String message) {
        super(status, message);
    }

}
