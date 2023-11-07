package com.example.orderservice.ui;

import com.example.orderservice.application.OrderDto.OrderRequestDto;
import com.example.orderservice.application.OrderDto.OrderResponseDto;
import com.example.orderservice.application.OrderDto.PaymentCompleteDto;
import com.example.orderservice.application.OrderFacade;
import com.example.orderservice.domain.OrderService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class OrderUserController {

    private final OrderFacade orderFacade;

    // 주문생성
    @PostMapping("/order")
    public ResponseEntity<Result> createOrder(@Valid @RequestBody OrderRequestDto dto,
                                            @RequestHeader(value = "user_id") String userId) {
        System.out.println("Order start" + userId);
        dto.setUserId(1L);
        OrderResponseDto responseDto = orderFacade.createOrder(dto);
        return ResponseEntity.ok(Result.createSuccessResult(responseDto));
    }

    // 결제완료
    @PostMapping("/payment/complete")
    public ResponseEntity<Result> paymentComplete(@RequestBody PaymentCompleteDto dto) {
        OrderResponseDto responseDto = orderFacade.paymentComplete(dto);
        return ResponseEntity.ok(Result.createSuccessResult(responseDto));
    }

}
