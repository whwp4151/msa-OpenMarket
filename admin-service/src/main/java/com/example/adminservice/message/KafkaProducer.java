package com.example.adminservice.message;

import com.example.adminservice.dto.BrandApprovedDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    public void approvedBrand(BrandApprovedDto dto) {
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send("approved-brand", jsonInString);
        log.info("kafka Producer sent data from the Admin microservice: {}", jsonInString);
    }

}
