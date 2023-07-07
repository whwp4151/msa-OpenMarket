package com.example.userservice.domain;

import com.example.userservice.dto.SignupDto;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Users {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="user_id", length = 50, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(length = 10, nullable = false)
    private String name;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    private LocalDateTime modDate;

    public static Users of(SignupDto signupDto) {
        return Users.builder()
            .userId(signupDto.getUserId())
            .password(signupDto.getPassword())
            .name(signupDto.getName())
            .build();
    }

}
