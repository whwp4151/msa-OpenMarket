package com.example.adminservice.service;

import com.example.adminservice.domain.Admin;
import com.example.adminservice.dto.AdminRequestDto;
import com.example.adminservice.dto.AdminResponseDto;
import com.example.adminservice.dto.Result;
import com.example.adminservice.exception.CustomException;
import com.example.adminservice.feign.client.BrandServiceClient;
import com.example.adminservice.feign.dto.BrandResponseDto;
import com.example.adminservice.feign.dto.TransactionDto.TransactionDepositRequestDto;
import com.example.adminservice.feign.dto.TransactionDto.TransactionResponseDto;
import com.example.adminservice.repository.AdminRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final BrandServiceClient brandServiceClient;

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

    public AdminResponseDto getAdmin(String userId) {
        return AdminResponseDto.of(findAdminByUserId(userId));
    }

    private Admin findAdminByUserId(String userId) {
        Admin admin = adminRepository.findByUserId(userId)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Admin not found"));
        return admin;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUserId(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(admin.getUserId(), admin.getPassword(),
            true, true, true, true,
            Collections.emptyList()
        );
    }

    public Result<List<BrandResponseDto>> getBrandApplications() {
        return brandServiceClient.getBrandApplications();
    }


    public Result<TransactionResponseDto> depositRequest(TransactionDepositRequestDto dto, String userId) {
        Admin admin = findAdminByUserId(userId);

        dto.setAdminId(admin.getId());

        return brandServiceClient.depositRequest(dto);
    }

    public Result<List<TransactionResponseDto>> getBrandTransactions(Long id) {
        return brandServiceClient.getBrandTransactions(id);
    }
}
