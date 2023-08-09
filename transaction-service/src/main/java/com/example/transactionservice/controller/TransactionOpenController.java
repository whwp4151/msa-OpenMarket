package com.example.transactionservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open")
public class TransactionOpenController {

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Transaction Service";
    }

}
