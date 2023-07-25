package com.example.userservice.dto;

import com.example.userservice.domain.User;
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

    public static UsersDto of(User user) {
        return UsersDto.builder()
            .id(user.getId())
            .userId(user.getUserId())
            .name(user.getName())
            .build();
    }

}
