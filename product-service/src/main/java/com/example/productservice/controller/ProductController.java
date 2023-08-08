package com.example.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Product Service";
    }

}