package com.example.userservice.controller;

import com.example.userservice.dto.Result;
import com.example.userservice.dto.SignupDto;
import com.example.userservice.dto.UsersDto;
import com.example.userservice.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open")
public class UsersOpenController {

    private final UserService userService;
    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in User Service"
            + ", token secret= " + env.getProperty("token.secret")
            + ", token expiration time=" + env.getProperty("token.expiration_time");
    }

    @PostMapping("/signup")
    public ResponseEntity<Result> signup(@RequestBody @Valid SignupDto signupDto) {
        UsersDto usersDto = userService.signup(signupDto);
        return ResponseEntity.ok(Result.createSuccessResult(usersDto));
    }

}
