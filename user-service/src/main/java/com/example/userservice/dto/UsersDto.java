package com.example.userservice.dto;

import com.example.userservice.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {

    private Long id;
    private String userId;
    private String name;

    public static UsersDto of(Users users) {
        return UsersDto.builder()
            .id(users.getId())
            .userId(users.getUserId())
            .name(users.getName())
            .build();
    }

}
