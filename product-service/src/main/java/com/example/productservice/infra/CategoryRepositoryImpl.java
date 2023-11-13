package com.example.productservice.infra;

import com.example.productservice.domain.Category;
import com.example.productservice.domain.CategoryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;
    private final CategoryQueryRepository categoryQueryRepository;

    @Override
    public Optional<Category> findById(String id) {
        return categoryJpaRepository.findById(id);
    }

    @Override
    public List<Category> getCategories(String id) {
        return categoryQueryRepository.getCategories(id);
    }
}
