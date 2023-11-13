package com.example.productservice.application;

import com.example.productservice.application.CategoryDto.ParentCategoryDto;
import com.example.productservice.application.ProductDto.CreateProductDto;
import com.example.productservice.application.ProductDto.ProductResponseDto;
import com.example.productservice.application.ProductDto.UpdateProductDto;
import com.example.productservice.domain.Product;
import com.example.productservice.domain.ProductLogService;
import com.example.productservice.domain.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductService productService;
    private final ProductLogService productLogService;

    public List<ParentCategoryDto> getCategories(String id) {
        return productService.getCategories(id);
    }

    public ProductResponseDto createProduct(CreateProductDto dto) {
        // 1. 상품 생성
        Product product = productService.createProduct(dto);

        // 2. 상품 로그 저장
        productLogService.saveProductLog(product);

        return ProductResponseDto.of(product);
    }

    public List<ProductResponseDto> getProduct(Long brandId) {
        return productService.getProduct(brandId);
    }

    public ProductResponseDto updateProduct(UpdateProductDto dto) {
        // 1. 상품 수정
        Product product = productService.updateProduct(dto);

        // 2. 상품 로그 저장
        productLogService.saveProductLog(product);

        return ProductResponseDto.of(product);
    }

}
