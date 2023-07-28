package com.example.adminservice.controller;

import com.example.adminservice.dto.Result;
import com.example.adminservice.feign.dto.BrandRequestDto;
import com.example.adminservice.service.AdminService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/brand/deposit-request")
    public ResponseEntity<Result> depositRequest(@RequestBody @Valid BrandDepositRequestDto dto,
                                                 @RequestHeader(value = "user_id") String userId) {
        return ResponseEntity.ok(adminService.createBrand(brandRequestDto));
    }

}
