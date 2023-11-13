package com.example.productservice.infra;

import com.example.productservice.ui.Result;
import com.example.productservice.application.order.OrderDto.OrderResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ORDER-SERVICE")
public interface OrderServiceFeign {

    @GetMapping("/order/{id}")
    Result<OrderResponseDto> getOrder(@PathVariable("id") Long id);

}
