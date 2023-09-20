package com.example.orderservice.message;

import com.example.orderservice.dto.OrderDto.PaymentCompleteDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    private static final String PAYMENT_COMPLETE_TOPIC = "payment-complete";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    public void paymentComplete(PaymentCompleteDto dto) {
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(PAYMENT_COMPLETE_TOPIC, jsonInString);
        log.info("kafka Producer sent data from the Brand microservice: {}", jsonInString);
    }

}
