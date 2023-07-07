package com.example.userservice.controller;

import com.example.userservice.dto.Result;
import com.example.userservice.dto.SignupDto;
import com.example.userservice.dto.UsersDto;
import com.example.userservice.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-service")
public class UsersController {

    private final UserService userService;

    @GetMapping("/user/health_check")
    public String status() {
        return "It's Working in User Service";
    }

    @PostMapping("/user/signup")
    public ResponseEntity<Result> signup(@RequestBody @Valid SignupDto signupDto) {
        UsersDto usersDto = userService.signup(signupDto);
        return ResponseEntity.ok(Result.createSuccessResult(usersDto));
    }

    @GetMapping("/getUser")
    public ResponseEntity<Result> getUser(@RequestHeader(value = "user_id") String userId) {
        UsersDto usersDto = userService.getUser(userId);
        return ResponseEntity.ok(Result.createSuccessResult(usersDto));
    }

}
