package com.example.adminservice.controller;

import com.example.adminservice.dto.AdminRequestDto;
import com.example.adminservice.dto.AdminResponseDto;
import com.example.adminservice.dto.Result;
import com.example.adminservice.service.AdminService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Brand Service"
            + ", token secret= " + env.getProperty("admin.secret")
            + ", token expiration time=" + env.getProperty("admin.expiration_time");
    }

    @PostMapping("admin")
    public ResponseEntity<Result> signup(@RequestBody @Valid AdminRequestDto dto) {
        AdminResponseDto responseDto = adminService.createAdmin(dto);
        return ResponseEntity.ok(Result.createSuccessResult(responseDto));
    }

}
