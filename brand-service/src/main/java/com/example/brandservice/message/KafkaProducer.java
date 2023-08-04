package com.example.brandservice.message;

import com.example.brandservice.feign.dto.TransactionDto.TransactionCreateDto;
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

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    public void createTransaction(TransactionCreateDto dto) {
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send("create-transaction", jsonInString);
        log.info("kafka Producer sent data from the Brand microservice: {}", jsonInString);
    }

}
