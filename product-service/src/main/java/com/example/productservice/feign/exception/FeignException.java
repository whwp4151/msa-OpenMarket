package com.example.productservice.feign.exception;

import com.example.productservice.exception.CustomException;
import org.springframework.http.HttpStatus;

public class FeignException extends CustomException {

    public FeignException(HttpStatus status, String message) {
        super(status, message);
    }

}
