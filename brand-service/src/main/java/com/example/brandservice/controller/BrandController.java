package com.example.brandservice.controller;

import com.example.brandservice.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;
    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Brand Service"
            + ", token secret= " + env.getProperty("brand.secret")
            + ", token expiration time=" + env.getProperty("brand.expiration_time");
    }

}
