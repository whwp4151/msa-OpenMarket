package com.example.userservice.controller;

import com.example.userservice.dto.Result;
import com.example.userservice.dto.UsersDto;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping("/getUser")
    public ResponseEntity<Result> getUser(@RequestHeader(value = "user_id") String userId) {
        UsersDto usersDto = userService.getUser(userId);
        return ResponseEntity.ok(Result.createSuccessResult(usersDto));
    }

}
