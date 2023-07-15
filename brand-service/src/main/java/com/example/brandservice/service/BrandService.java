package com.example.brandservice.service;

import com.example.brandservice.repository.BrandsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandsRepository brandsRepository;

}
