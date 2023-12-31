package com.example.adminservice.controller;

import com.example.adminservice.dto.BrandApprovedDto;
import com.example.adminservice.dto.Result;
import com.example.adminservice.feign.dto.TransactionDto;
import com.example.adminservice.service.AdminService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/brand/applications")
    public ResponseEntity<Result> getBrandApplications() {
        return ResponseEntity.ok(adminService.getBrandApplications());
    }

    @PostMapping("/brand/deposit-request")
    public ResponseEntity<Result> depositRequest(@RequestBody @Valid TransactionDto.TransactionDepositRequestDto dto,
                                                 @RequestHeader(value = "user_id") String userId) {
        return ResponseEntity.ok(adminService.depositRequest(dto, userId));
    }

    @GetMapping("/brand/transactions/{id}")
    public ResponseEntity<Result> getBrandTransactions(@PathVariable("id") Long id) {
        return ResponseEntity.ok(adminService.getBrandTransactions(id));
    }

    @PostMapping("/brand/approve/{id}")
    public ResponseEntity<Result> approvedBrand(@PathVariable("id") Long id) {
        BrandApprovedDto brandApprovedDto = adminService.approvedBrand(id);
        return ResponseEntity.ok(Result.createSuccessResult(brandApprovedDto));
    }

}
