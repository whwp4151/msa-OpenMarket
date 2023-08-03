package com.example.brandservice.service;

import com.example.brandservice.domain.BankInfo;
import com.example.brandservice.domain.BrandAccount;
import com.example.brandservice.domain.Brand;
import com.example.brandservice.domain.enums.BrandStatus;
import com.example.brandservice.dto.BrandAccountDto;
import com.example.brandservice.dto.BrandAccountRequestDto;
import com.example.brandservice.dto.BrandApprovedDto;
import com.example.brandservice.dto.BrandRequestDto;
import com.example.brandservice.dto.BrandResponseDto;
import com.example.brandservice.dto.Result;
import com.example.brandservice.feign.client.TransactionServiceClient;
import com.example.brandservice.feign.dto.TransactionDto.DepositDto;
import com.example.brandservice.feign.dto.TransactionDto.TransactionDepositRequestDto;
import com.example.brandservice.feign.dto.TransactionDto.TransactionResponseDto;
import com.example.brandservice.exception.CustomException;
import com.example.brandservice.repository.BrandAccountRepository;
import com.example.brandservice.repository.BrandsRepository;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
    private final TransactionServiceClient transactionServiceClient;

    @Transactional
    public Result createBrand(BrandRequestDto brandRequestDto, String userId) {
        BrandAccount brandAccount = this.findByLoginIdWithBrand(userId);

        if (brandAccount.getBrand() != null) {
            return Result.createErrorResult("Brand Already created");
        }

        Brand brand = Brand.create(brandRequestDto.getName(), brandAccount);
        brandsRepository.save(brand);

        return Result.createSuccessResult(BrandResponseDto.of(brand));
    }

    @Transactional
    public BrandAccountDto createBrandAccount(BrandAccountRequestDto brandAccountRequestDto) {
        validateDuplicateLoginId(brandAccountRequestDto.getLoginId());

        String password = passwordEncoder.encode(brandAccountRequestDto.getPassword());

        BrandAccount brandAccount = BrandAccount.create(brandAccountRequestDto.getLoginId(), password,
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
        return BrandAccountDto.of(findBrandAccountByLoginId(loginId));
    }

    private BrandAccount findBrandAccountByLoginId(String loginId) {
        return brandAccountRepository.findByLoginId(loginId)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Brand Account not found"));
    }

    private Brand findBrandById(Long id) {
        return brandsRepository.findById(id)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Brand not found"));
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

    public List<BrandResponseDto> getBrandApplications() {
        List<Brand> brands = brandsRepository.findByStatus(BrandStatus.PENDING);

        return brands.stream()
            .map(BrandResponseDto::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public TransactionResponseDto depositRequest(TransactionDepositRequestDto dto) {
        Brand brand = this.findBrandById(dto.getBrandId());
        brand.setAdmin(dto.getAdminId());

        // todo. kafka
//        {
//            "status": "success",
//            "message": "예치금이 성공적으로 입금되었습니다.",
//            "depositAmount": 100000,
//            "transactionId": "abc123xyz"
//        }
//        Transaction transaction = Transaction.create(brand, dto.getAmount(), TransactionType.REQUEST);
//        transactionRepository.save(transaction);

        return new TransactionResponseDto();
    }

    public BrandAccount findByLoginIdWithBrand(String userId) {
        return brandAccountRepository.findByLoginIdWithBrand(userId)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Brand Account not found"));
    }

    public Result<List<TransactionResponseDto>> getDepositRequest(Long brandId) {
        return transactionServiceClient.getDepositRequest(brandId);
    }

    @Transactional
    public TransactionResponseDto deposit(DepositDto dto, String userId) {
        BrandAccount brandAccount = this.findByLoginIdWithBrand(userId);

        // todo. kafka
//        {
//            "status": "success",
//            "message": "예치금이 성공적으로 입금되었습니다.",
//            "depositAmount": 100000,
//            "transactionId": "abc123xyz"
//        }
//        Transaction transaction = Transaction.create(brandAccount.getBrand(), dto.getAmount(), TransactionType.DEPOSIT);
//        transactionRepository.save(transaction);

        return new TransactionResponseDto();
    }

    @Transactional
    public void approvedBrand(BrandApprovedDto dto) {
        Brand brand = brandsRepository.findById(dto.getBrandId())
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Brand not found"));

        brand.approve();
    }
}
