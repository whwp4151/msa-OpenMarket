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
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Category diaryPlanner = Category.createParentCategory("1000", "다이어리/플래너");
            Category diary = Category.createSubCategory("1001", "다이어리", diaryPlanner);
            Category organizer = Category.createSubCategory("1002", "오거나이저", diaryPlanner);
            Category planner = Category.createSubCategory("1003", "플래너", diaryPlanner);

            em.persist(diaryPlanner);
        }

        public void dbInit2() {
            Category notePaper = Category.createParentCategory("2000", "노트/메모지");
            Category paper = Category.createSubCategory("2001", "메모지", notePaper);
            Category wordBook = Category.createSubCategory("2002", "단어장", notePaper);
            Category note = Category.createSubCategory("2003", "수첩", notePaper);

            em.persist(notePaper);
        }

        public void dbInit3() {
            Category decoration = Category.createParentCategory("3000", "데코레이션");
            Category stamp = Category.createSubCategory("3001", "스탬프", decoration);
            Category sticker = Category.createSubCategory("3002", "스티커", decoration);
            Category tape = Category.createSubCategory("3003", "테이프", decoration);

            em.persist(decoration);
        }

    }

}
