package com.example.productservice.domain;

import com.example.productservice.application.CategoryDto.ParentCategoryDto;
import com.example.productservice.application.ProductDto.CreateProductDto;
import com.example.productservice.application.ProductDto.ProductOptionDto;
import com.example.productservice.application.ProductDto.ProductResponseDto;
import com.example.productservice.application.ProductDto.UpdateProductDto;
import com.example.productservice.security.CustomException;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<ParentCategoryDto> getCategories(String id) {
        return categoryRepository.getCategories(id).stream()
            .map(ParentCategoryDto::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public Product createProduct(CreateProductDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Category not found"));

        if (category.getParentCategory() == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Cannot create a product under a top-level category");
        }

        Product product = Product.create(dto.getName(), dto.getPrice(), dto.getConsumerPrice(), dto.getDiscountedRate(), dto.getIsSold(), dto.getBrandId(), category);

        if (!CollectionUtils.isEmpty(dto.getProductOptions())) {
            for (ProductOptionDto optionDto : dto.getProductOptions()) {
                ProductOption productOption = ProductOption.create(optionDto.getName(), optionDto.getAddPrice(), optionDto.getStock());
                product.addProductOption(productOption);
            }
        }

        return productRepository.save(product);
    }

    public List<ProductResponseDto> getProduct(Long brandId) {
        return productRepository.findByBrandId(brandId).stream()
            .map(ProductResponseDto::of)
            .collect(Collectors.toList());
    }

    public Product getProductEntity(Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @Transactional
    public Product updateProduct(UpdateProductDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Category not found"));

        if (category.getParentCategory() == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Cannot create a product under a top-level category");
        }

        Product product = productRepository.findByIdWithProductOption(dto.getProductId());
        if (product == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Product not found");
        }

        product.updateProductInfo(dto.getName(), dto.getPrice(), dto.getConsumerPrice(), dto.getDiscountedRate(), dto.getStatus(), category, dto.getProductOptions());

        return product;
    }

}
