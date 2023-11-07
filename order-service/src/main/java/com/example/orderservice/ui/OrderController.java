package com.example.orderservice.ui;

import com.example.orderservice.application.OrderDto.OrderResponseDto;
import com.example.orderservice.application.OrderFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;

    @GetMapping("/order/{id}")
    public ResponseEntity<Result> getOrder(@PathVariable("id") Long id) {
        OrderResponseDto order = orderFacade.getOrder(id);
        return ResponseEntity.ok(Result.createSuccessResult(order));
    }

}
