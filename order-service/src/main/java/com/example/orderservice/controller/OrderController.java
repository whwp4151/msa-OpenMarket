package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto.OrderResponseDto;
import com.example.orderservice.dto.Result;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order/{id}")
    public ResponseEntity<Result> getOrder(@PathVariable("id") Long id) {
        OrderResponseDto order = orderService.getOrder(id);
        return ResponseEntity.ok(Result.createSuccessResult(order));
    }

}
