package com.example.productservice.service;

import com.example.productservice.domain.Category;
import com.example.productservice.domain.Product;
import com.example.productservice.dto.CategoryDto.ParentCategoryDto;
import com.example.productservice.dto.ProductDto.CreateProductDto;
import com.example.productservice.dto.ProductDto.ProductResponseDto;
import com.example.productservice.exception.CustomException;
import com.example.productservice.repository.CategoryCustomRepository;
import com.example.productservice.repository.CategoryRepository;
import com.example.productservice.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final CategoryCustomRepository categoryCustomRepository;
    private final ProductRepository productRepository;

    public List<ParentCategoryDto> getCategories() {
        return categoryCustomRepository.getCategories(null).stream()
            .map(ParentCategoryDto::of)
            .collect(Collectors.toList());
    }

    public List<ParentCategoryDto> getCategories(Long id) {
        return categoryCustomRepository.getCategories(id).stream()
            .map(ParentCategoryDto::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponseDto createProduct(CreateProductDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Category not found"));

        Product product = Product.create(dto.getName(), dto.getPrice(), dto.getStockQuantity(), dto.getBrandId(), category);
        productRepository.save(product);

        return ProductResponseDto.of(product);
    }

}
