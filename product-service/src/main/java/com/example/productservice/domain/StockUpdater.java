package com.example.productservice.domain;

import com.example.productservice.application.order.OrderDto.OrderResponseDto;
import com.example.productservice.application.order.OrderDto.PaymentCompleteDto;
import com.example.productservice.security.DistributedLock;
import com.example.productservice.security.CustomException;
import com.example.productservice.application.order.OrderDto.OrderItemDto;
import com.example.productservice.ui.Result;
import com.example.productservice.ui.Result.Code;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockUpdater {

    private final ProductRepository productRepository;
    private final OrderServiceClient orderServiceClient;

    @Transactional
    public void updateStock(PaymentCompleteDto dto) {
        OrderResponseDto order = this.getOrder(dto.getOrderId());
        this.updateProductStock(order.getOrderItems());
    }

    @DistributedLock(key = "stock_lock", waitTime = 3000, leaseTime = 3000, timeUnit = TimeUnit.MILLISECONDS)
    public void updateProductStock(List<OrderItemDto> orderItems) {
        for (OrderItemDto item : orderItems) {
            if (!checkStockAndUpdate(item.getProductId(), item.getProductOptionId(), item.getQuantity())) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "need more stock");
            }
        }
    }

    private boolean checkStockAndUpdate(Long productId, Long productOptionId, int orderQuantity) {
        Product product = productRepository.findByIdWithProductOption(productId);

        if (product == null) return false;

        ProductOption productOption = product.getProductOptions().stream()
            .filter(option -> option.getId().equals(productOptionId))
            .findFirst()
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Product option not found"));

        productOption.removeStock(orderQuantity);
        productRepository.save(product);
        return true;
    }

    private OrderResponseDto getOrder(Long orderId) {
        Result<OrderResponseDto> orderResult = orderServiceClient.getOrder(orderId);
        if (Code.ERROR == orderResult.getCode()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Order Not found.");
        }
        return orderResult.getData();
    }

}
