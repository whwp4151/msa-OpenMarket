package com.example.productservice.domain;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> findById(String id);
    public List<Category> getCategories(String id);

}
