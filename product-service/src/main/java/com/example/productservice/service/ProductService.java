package com.example.productservice.service;

import com.example.productservice.dto.CategoryDto.ParentCategoryDto;
import com.example.productservice.repository.CategoryCustomRepository;
import com.example.productservice.repository.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryCustomRepository categoryCustomRepository;

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

}
