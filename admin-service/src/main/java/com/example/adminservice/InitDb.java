package com.example.adminservice;

import com.example.adminservice.domain.Admin;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        
        private final EntityManager em;

        private final BCryptPasswordEncoder passwordEncoder;

        public void dbInit1() {
            Admin admin1 = createAdmin("admin", passwordEncoder.encode("1234"), "관리자1");
            Admin admin2 = createAdmin("admin2", passwordEncoder.encode("1234"), "관리자2");

            persistEntities(admin1, admin2);
        }

        public void dbInit2() {
            Admin admin1 = createAdmin("admin3", passwordEncoder.encode("1234"), "관리자3");
            Admin admin2 = createAdmin("admin4", passwordEncoder.encode("1234"), "관리자4");

            persistEntities(admin1, admin2);
        }

        private void persistEntities(Object... entities) {
            for (Object entity : entities) {
                em.persist(entity);
            }
        }

        private Admin createAdmin(String userId, String password, String name) {
            return Admin.create(userId, password, name);
        }

    }

}
