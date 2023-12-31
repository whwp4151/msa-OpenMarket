package com.example.userservice.exception;

import com.example.userservice.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity customExceptionHandler(CustomException ce) {
        HttpStatus status = ce.getStatus();
        Result errorResult = ce.getErrorResult();

        return ResponseEntity
            .status(status)
            .body(errorResult);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity bindExceptionHandler(BindException exception) {
        return getValidationErrorBody(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        return getValidationErrorBody(exception);
    }

    private ResponseEntity getValidationErrorBody(BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        StringBuilder builder = new StringBuilder();
        bindingResult.getFieldErrors()
            .forEach(fieldError -> {
                builder.append("[Field: ");
                builder.append(fieldError.getField());
                builder.append("], [DefaultMessage: ");
                builder.append(fieldError.getDefaultMessage());
                builder.append("], [RejectedValue: ");
                builder.append(fieldError.getRejectedValue());
                builder.append("]");
            });

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Result.createErrorResult(builder.toString()));
    }
}
