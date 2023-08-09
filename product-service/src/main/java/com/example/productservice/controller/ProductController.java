package com.example.productservice.controller;

import com.example.productservice.dto.CategoryDto.ParentCategoryDto;
import com.example.productservice.dto.Result;
import com.example.productservice.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/categories")
    public ResponseEntity<Result> getCategories() {
        List<ParentCategoryDto> categories = productService.getCategories();
        return ResponseEntity.ok(Result.createSuccessResult(categories));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Result> getCategories(@PathVariable("id") Long id) {
        List<ParentCategoryDto> categories = productService.getCategories(id);
        return ResponseEntity.ok(Result.createSuccessResult(categories));
    }

}
