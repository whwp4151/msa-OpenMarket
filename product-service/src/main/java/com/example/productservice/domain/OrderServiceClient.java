package com.example.productservice.domain;

import com.example.productservice.application.order.OrderDto.OrderResponseDto;
import com.example.productservice.ui.Result;

public interface OrderServiceClient {

    Result<OrderResponseDto> getOrder(Long id);

}
