package com.example.productservice.service;

import com.example.productservice.annotation.DistributedLock;
import com.example.productservice.domain.Product;
import com.example.productservice.domain.ProductOption;
import com.example.productservice.exception.CustomException;
import com.example.productservice.message.dto.OrderDto.OrderItemDto;
import com.example.productservice.repository.ProductCustomRepository;
import com.example.productservice.repository.ProductRepository;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockUpdater {

    private final ProductRepository productRepository;
    private final ProductCustomRepository productCustomRepository;

    @DistributedLock(key = "stock_lock", waitTime = 3000, leaseTime = 3000, timeUnit = TimeUnit.MILLISECONDS)
    public void updateProductStock(List<OrderItemDto> orderItems) {
        for (OrderItemDto item : orderItems) {
            if (!checkStockAndUpdate(item.getProductId(), item.getProductOptionId(), item.getQuantity())) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "need more stock");
            }
        }
    }

    private boolean checkStockAndUpdate(Long productId, Long productOptionId, int orderQuantity) {
        Product product = productCustomRepository.findByIdWithProductOption(productId);

        if (product == null) return false;

        ProductOption productOption = product.getProductOptions().stream()
            .filter(option -> option.getId().equals(productOptionId))
            .findFirst()
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Product option not found"));

        productOption.removeStock(orderQuantity);
        productRepository.save(product);
        return true;
    }

}
