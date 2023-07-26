package com.example.adminservice.feign.exception;

import com.example.adminservice.exception.CustomException;
import org.springframework.http.HttpStatus;

public class FeignException extends CustomException {

    public FeignException(HttpStatus status, String message) {
        super(status, message);
    }

}
