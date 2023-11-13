package com.example.productservice.ui.api;

import com.example.productservice.application.CategoryDto.ParentCategoryDto;
import com.example.productservice.application.ProductDto.CreateProductDto;
import com.example.productservice.application.ProductDto.ProductResponseDto;
import com.example.productservice.application.ProductFacade;
import com.example.productservice.ui.Result;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductFacade productFacade;

    @GetMapping("/categories")
    public ResponseEntity<Result> getCategories() {
        List<ParentCategoryDto> categories = productFacade.getCategories(null);
        return ResponseEntity.ok(Result.createSuccessResult(categories));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Result> getCategories(@PathVariable("id") String id) {
        List<ParentCategoryDto> categories = productFacade.getCategories(id);
        return ResponseEntity.ok(Result.createSuccessResult(categories));
    }

    @PostMapping("/product")
    public ResponseEntity<Result> createProduct(@RequestBody CreateProductDto dto) {
        ProductResponseDto responseDto = productFacade.createProduct(dto);
        return ResponseEntity.ok(Result.createSuccessResult(responseDto));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Result> getProduct(@PathVariable("id") Long id) {
        List<ProductResponseDto> list = productFacade.getProduct(id);
        return ResponseEntity.ok(Result.createSuccessResult(list));
    }

}
