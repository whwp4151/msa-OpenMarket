package com.example.transactionservice.message;

import com.example.transactionservice.Service.TransactionService;
import com.example.transactionservice.dto.TransactionDto.TransactionCreateDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper mapper;
    private final TransactionService transactionService;

    @KafkaListener(topics = "create-transaction")
    public void createTransaction(String kafkaMessage) {
        log.info("kafkaMessage :: {}", kafkaMessage);

        TransactionCreateDto dto;
        try {
            dto = mapper.readValue(kafkaMessage, TransactionCreateDto.class);
            transactionService.createTransaction(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
