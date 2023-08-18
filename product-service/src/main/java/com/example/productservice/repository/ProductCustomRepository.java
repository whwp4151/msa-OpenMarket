package com.example.productservice.repository;

import static com.example.productservice.domain.QProduct.product;
import static com.example.productservice.domain.QProductOption.productOption;

import com.example.productservice.domain.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductCustomRepository {

    private final JPAQueryFactory queryFactory;

    public Product findByIdWithProductOption(Long id) {
        return queryFactory.select(product)
            .from(product)
            .leftJoin(product.productOptions, productOption).fetchJoin()
            .where(product.id.eq(id))
            .distinct()
            .fetchOne();
    }

}
