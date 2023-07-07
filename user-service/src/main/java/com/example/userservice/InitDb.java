package com.example.userservice;

import com.example.userservice.domain.Users;
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
            Users user1 = createUser("test", passwordEncoder.encode("1234"), "테스트");
            Users user2 = createUser("test2", passwordEncoder.encode("1234"), "테스트2");

            persistEntities(user1, user2);
        }

        public void dbInit2() {
            Users user1 = createUser("user", passwordEncoder.encode("1234"), "유저");
            Users user2 = createUser("user2", passwordEncoder.encode("1234"), "유저2");

            persistEntities(user1, user2);
        }

        private void persistEntities(Object... entities) {
            for (Object entity : entities) {
                em.persist(entity);
            }
        }

        private Users createUser(String test, String password, String name) {
            return Users.createUser(test, password, name);
        }

    }

}
