package com.example.productservice.feign.client;

import com.example.productservice.dto.Result;
import com.example.productservice.message.dto.OrderDto.OrderResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ORDER-SERVICE")
public interface OrderServiceClient {

    @GetMapping("/order/{id}")
    Result<OrderResponseDto> getOrder(@PathVariable("id") Long id);

}
