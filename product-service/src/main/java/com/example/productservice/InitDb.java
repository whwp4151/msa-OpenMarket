package com.example.productservice;

import com.example.productservice.domain.Category;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// 테스트용
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
        initService.dbInit3();
        initService.dbInit4();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Category top = Category.createParentCategory("상의");
            Category shortSleeve = Category.createSubCategory("반소매 티셔츠", top);
            Category longSleeve = Category.createSubCategory("긴소매 티셔츠", top);
            Category knit = Category.createSubCategory("니트", top);

            em.persist(top);
        }

        public void dbInit2() {
            Category outer = Category.createParentCategory("아우터");
            Category jacket = Category.createSubCategory("자켓", outer);
            Category coat = Category.createSubCategory("코트", outer);
            Category padding = Category.createSubCategory("패딩", outer);

            em.persist(outer);
        }

        public void dbInit3() {
            Category pants = Category.createParentCategory("바지");
            Category denim = Category.createSubCategory("데님 팬츠", pants);
            Category cotton = Category.createSubCategory("코튼 팬츠", pants);
            Category slacks = Category.createSubCategory("슬랙스", pants);

            em.persist(pants);
        }

        public void dbInit4() {
            Category shoes = Category.createParentCategory("신발");
            Category sneakers = Category.createSubCategory("스니커즈", shoes);
            Category dressShoes = Category.createSubCategory("구두", shoes);
            Category slipper = Category.createSubCategory("슬리퍼", shoes);

            em.persist(shoes);
        }

    }

}
