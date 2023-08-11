package com.example.brandservice.feign.client;

import com.example.brandservice.dto.Result;
import com.example.brandservice.feign.dto.ProductDto.CreateProductDto;
import com.example.brandservice.feign.dto.ProductDto.ProductResponseDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("PRODUCT-SERVICE")
public interface ProductServiceClient {

    @PostMapping("/product")
    Result<ProductResponseDto> createProduct(@RequestBody CreateProductDto dto);

    @GetMapping("/product/{id}")
    Result<List<ProductResponseDto>> getProduct(@PathVariable("id") Long id);

}
