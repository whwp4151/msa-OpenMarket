package com.example.productservice.message;

import com.example.productservice.message.dto.OrderDto.PaymentCompleteDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaPaymentListener implements AcknowledgingMessageListener<String, String> {
    private static final String PAYMENT_COMPLETE_TOPIC = "payment-complete";

    private final ObjectMapper mapper;

    @Override
    @KafkaListener(topics = PAYMENT_COMPLETE_TOPIC, groupId = KafkaConsumerConfig.PAYMENTS_CONSUMER_GROUP)
    public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment) {
        log.info("kafkaMessage :: {}", data.value());
        try {
            PaymentCompleteDto dto = mapper.readValue(data.value(), PaymentCompleteDto.class);
            // todo. 재고관리

            // 예외가 발생하지 않는다면, 수동커밋을 진행하여 읽은 메세지는 처리됐음을 보장한다.!
            acknowledgment.acknowledge();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
