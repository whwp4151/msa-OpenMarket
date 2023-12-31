package com.example.productservice.infra;

import com.example.productservice.domain.Category;
import com.example.productservice.domain.QCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Category> getCategories(String id) {
        QCategory parent = new QCategory("parent");
        QCategory sub = new QCategory("sub");

        return queryFactory.select(parent)
            .from(parent)
            .leftJoin(parent.subCategories, sub).fetchJoin()
            .where(
                parent.parentCategory.isNull()
                    .and(idEq(parent, id))
            )
            .distinct()
            .fetch();
    }

    private BooleanExpression idEq(QCategory category, String  id) {
        if (id == null) return null;
        return category.categoryId.eq(id);
    }

}
