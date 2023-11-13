package com.example.productservice.domain;

import com.example.productservice.infra.ProductLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductLogService {

    private final ProductLogRepository productLogRepository;

    public void saveProductLog(Product product) {
        ProductLog productLog = ProductLog.fromProduct(product);
        productLogRepository.save(productLog);
    }

}
