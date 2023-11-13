package com.example.productservice.infra;


import com.example.productservice.application.order.OrderDto.OrderResponseDto;
import com.example.productservice.domain.OrderServiceClient;
import com.example.productservice.ui.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderServiceClientImpl implements OrderServiceClient {

    private final OrderServiceFeign orderServiceFeign;

    @Override
    public Result<OrderResponseDto> getOrder(Long id) {
        return orderServiceFeign.getOrder(id);
    }
}
