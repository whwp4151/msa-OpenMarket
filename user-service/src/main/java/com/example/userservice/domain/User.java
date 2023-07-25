package com.example.userservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(length = 10, nullable = false)
    private String name;

    @Builder
    public User(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public static User create(String userId, String password, String name) {
        return User.builder()
            .userId(userId)
            .password(password)
            .name(name)
            .build();
    }

}
