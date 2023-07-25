package com.example.brandservice.service;

import com.example.brandservice.domain.BankInfo;
import com.example.brandservice.domain.BrandAccount;
import com.example.brandservice.domain.Brand;
import com.example.brandservice.dto.BrandAccountDto;
import com.example.brandservice.dto.BrandAccountRequestDto;
import com.example.brandservice.dto.BrandRequestDto;
import com.example.brandservice.dto.BrandResponseDto;
import com.example.brandservice.exception.CustomException;
import com.example.brandservice.repository.BrandAccountRepository;
import com.example.brandservice.repository.BrandsRepository;
import java.util.Collections;
import javax.transaction.Transactional;
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
public class BrandService implements UserDetailsService {

    private final BrandsRepository brandsRepository;
    private final BrandAccountRepository brandAccountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public BrandResponseDto createBrand(BrandRequestDto brandRequestDto) {
        Brand brand = Brand.create(brandRequestDto.getName(), brandRequestDto.getDepositAmount(), brandRequestDto.getAdminId(), brandRequestDto.getIsActive());
        brandsRepository.save(brand);

        return BrandResponseDto.of(brand);
    }

    @Transactional
    public BrandAccountDto createBrandAccount(BrandAccountRequestDto brandAccountRequestDto) {
        validateDuplicateLoginId(brandAccountRequestDto.getLoginId());

        Brand brand = brandsRepository.findById(brandAccountRequestDto.getBrandId())
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Brand not found"));

        String password = passwordEncoder.encode(brandAccountRequestDto.getPassword());

        BrandAccount brandAccount = BrandAccount.create(brand, brandAccountRequestDto.getLoginId(), password,
            new BankInfo(brandAccountRequestDto.getAccountNumber(), brandAccountRequestDto.getBankName(), brandAccountRequestDto.getHolderName()));

        brandAccountRepository.save(brandAccount);

        return BrandAccountDto.of(brandAccount);
    }

    private void validateDuplicateLoginId(String loginId) {
        if (brandAccountRepository.existsByLoginId(loginId)) {
            throw new CustomException(HttpStatus.CONFLICT, "Duplicate Login Id");
        }
    }

    public BrandAccountDto getBrandAccount(String loginId) {
        BrandAccount brandAccount = brandAccountRepository.findByLoginId(loginId)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Brand Account not found"));

        return BrandAccountDto.of(brandAccount);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BrandAccount brandAccount = brandAccountRepository.findByLoginId(username)
            .orElseThrow(() -> new UsernameNotFoundException("Brand Account not found"));

        return new User(brandAccount.getLoginId(), brandAccount.getPassword(),
            true, true, true, true,
            Collections.emptyList()
        );
    }

}
