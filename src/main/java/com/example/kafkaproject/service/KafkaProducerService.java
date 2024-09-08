package com.example.kafkaproject.service;

import com.example.kafkaproject.constant.KafkaConstant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Getter
@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final ArrayList<String> consumedKafkaMessages;
    private final SimpMessagingTemplate stompTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    public void createKafkaMessage(String message) {
        kafkaTemplate.send(KafkaConstant.KAFKA_TOPIC, message);
    }
}