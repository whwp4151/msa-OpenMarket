package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto.OrderRequestDto;
import com.example.orderservice.dto.OrderDto.OrderResponseDto;
import com.example.orderservice.dto.OrderDto.PaymentCompleteDto;
import com.example.orderservice.dto.Result;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class OrderUserController {

    private final OrderService orderService;

    // 주문생성
    @PostMapping("/order")
    public ResponseEntity<Result> createOrder(@RequestBody OrderRequestDto dto) {
        OrderResponseDto responseDto = orderService.createOrder(dto);
        return ResponseEntity.ok(Result.createSuccessResult(responseDto));
    }

    // 결제완료
    @PostMapping("/payment/complete")
    public ResponseEntity<Result> paymentComplete(@RequestBody PaymentCompleteDto dto) {
        OrderResponseDto responseDto = orderService.paymentComplete(dto);
        return ResponseEntity.ok(Result.createSuccessResult(responseDto));
    }

}
