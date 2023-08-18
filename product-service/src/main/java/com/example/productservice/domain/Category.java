package com.example.productservice.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    private String categoryId;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<Category> subCategories = new ArrayList<>();

    @Builder
    public Category(String categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public static Category createParentCategory(String categoryId, String name) {
        return Category.builder()
            .categoryId(categoryId)
            .name(name)
            .build();
    }

    public static Category createSubCategory(String categoryId, String name, Category parentCategory) {
        Category subCategory = Category.builder()
            .categoryId(categoryId)
            .name(name)
            .build();

        parentCategory.addSubCategory(subCategory);
        return subCategory;
    }

    public void addSubCategory(Category subCategory) {
        this.subCategories.add(subCategory);
        subCategory.setParentCategory(this);
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

}
