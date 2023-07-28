package com.example.brandservice.controller;

import com.example.brandservice.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class BrandAdminController {

    private final BrandService brandService;

}
