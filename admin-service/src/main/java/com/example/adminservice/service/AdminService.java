package com.example.adminservice.service;

import com.example.adminservice.domain.Admin;
import com.example.adminservice.dto.AdminRequestDto;
import com.example.adminservice.dto.AdminResponseDto;
import com.example.adminservice.exception.CustomException;
import com.example.adminservice.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminResponseDto createAdmin(AdminRequestDto dto) {
        validateDuplicateUserId(dto.getUserId());

        String password = passwordEncoder.encode(dto.getPassword());

        Admin admin = Admin.create(dto.getUserId(), password, dto.getName());
        adminRepository.save(admin);

        return AdminResponseDto.of(admin);
    }

    private void validateDuplicateUserId(String userId) {
        if (adminRepository.existsByUserId(userId)) {
            throw new CustomException(HttpStatus.CONFLICT, "Duplicate User Id");
        }
    }

}
