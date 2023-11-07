package com.example.orderservice.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.orderservice.domain.OrderService;
import com.example.orderservice.application.OrderDto.OrderItemDto;
import com.example.orderservice.application.OrderDto.OrderRequestDto;
import com.example.orderservice.application.OrderDto.OrderResponseDto;
import com.example.orderservice.application.OrderDto.PaymentCompleteDto;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void createOrder() throws Exception {
        //given
        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setUserId(1L);
        requestDto.setDeliveryFee(2500);
        requestDto.setCity("서울");
        requestDto.setStreet("1길");
        requestDto.setZipcode("111111");

        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        OrderItemDto orderItemDto1 = new OrderItemDto();
        orderItemDto1.setProductId(1L);
        orderItemDto1.setProductName("사랑의 물결 일기장 : 복숭아");
        orderItemDto1.setProductOptionId(1L);
        orderItemDto1.setProductOptionName("그리드");
        orderItemDto1.setProductPrice(38000);
        orderItemDto1.setProductVersion(1);
        orderItemDto1.setOrderPrice(34500);
        orderItemDto1.setQuantity(3);

        OrderItemDto orderItemDto2 = new OrderItemDto();
        orderItemDto2.setProductId(1L);
        orderItemDto2.setProductName("사랑의 물결 일기장 : 복숭아");
        orderItemDto2.setProductOptionId(2L);
        orderItemDto2.setProductOptionName("라인");
        orderItemDto2.setProductPrice(35000);
        orderItemDto2.setProductVersion(1);
        orderItemDto2.setOrderPrice(31500);
        orderItemDto2.setQuantity(2);

        orderItemDtos.add(orderItemDto1);
        orderItemDtos.add(orderItemDto2);
        requestDto.setOrderItems(orderItemDtos);

        //when
        OrderResponseDto responseDto = orderService.createOrder(requestDto);

        //then
        assertEquals("주문접수", responseDto.getOrderStatus());
        assertEquals(166500, responseDto.getTotalPrice());
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void paymentComplete() throws Exception {
        //given
        PaymentCompleteDto request = new PaymentCompleteDto();
        request.setOrderId(1L);
        request.setAmount(166500);

        //when
        OrderResponseDto responseDto = orderService.paymentComplete(request);

        //then
        assertEquals("결제완료", responseDto.getOrderStatus());
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void confirmPayment() throws Exception {
        //given
        Long orderId = 1L;

        //when
        OrderResponseDto responseDto = orderService.confirmPayment(orderId);

        //then
        assertEquals("상품준비중", responseDto.getOrderStatus());
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void preparingDelivery() throws Exception {
        //given
        Long orderId = 1L;

        //when
        OrderResponseDto responseDto = orderService.preparingDelivery(orderId);

        //then
        assertEquals("배송준비중", responseDto.getOrderStatus());
    }

}