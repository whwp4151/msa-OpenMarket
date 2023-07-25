package com.example.adminservice.dto;

import com.example.adminservice.domain.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDto {

    private Long id;

    private String userId;

    private String name;

    public static AdminResponseDto of(Admin admin) {
        return AdminResponseDto.builder()
            .id(admin.getId())
            .userId(admin.getUserId())
            .name(admin.getName())
            .build();
    }

}
