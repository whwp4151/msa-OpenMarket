package com.example.productservice.dto;

import com.example.productservice.domain.Category;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;

public class CategoryDto {

    @Data
    @Builder
    public static class ParentCategoryDto {

        private Long categoryId;

        private String name;

        private List<SubCategoryDto> subCategories;

        public static ParentCategoryDto of(Category category) {
            return ParentCategoryDto.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .subCategories(category.getSubCategories().stream()
                    .map(SubCategoryDto::of)
                    .collect(Collectors.toList()))
                .build();
        }

    }

    @Data
    @Builder
    public static class SubCategoryDto {

        private Long categoryId;

        private String name;

        public static SubCategoryDto of(Category category) {
            return SubCategoryDto.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .build();
        }

    }

}
