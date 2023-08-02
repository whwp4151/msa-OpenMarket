package com.example.brandservice.message;

import com.example.brandservice.dto.BrandApprovedDto;
import com.example.brandservice.service.BrandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final ObjectMapper mapper;
    private final BrandService brandService;

    @KafkaListener(topics = "approved-brand")
    public void approvedBrand(String kafkaMessage) {
        log.info("kafkaMessage :: {}", kafkaMessage);

        BrandApprovedDto dto;
        try {
            dto = mapper.readValue(kafkaMessage, BrandApprovedDto.class);
            brandService.approvedBrand(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
