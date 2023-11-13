package com.example.productservice.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(Long id);

    Product save(Product product);

    List<Product> findByBrandId(Long brandId);

    public Product findByIdWithProductOption(Long id);

}
