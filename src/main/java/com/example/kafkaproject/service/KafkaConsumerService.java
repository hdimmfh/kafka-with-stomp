package com.example.kafkaproject.service;

import com.example.kafkaproject.constant.KafkaConstant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Getter
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final ArrayList<String> consumedKafkaMessages;
    private final SimpMessagingTemplate stompTemplate;

    // Method to handle incoming(polled) Kafka messages.
    @KafkaListener(topics = KafkaConstant.KAFKA_TOPIC, groupId = KafkaConstant.GROUP_ID)
    public void listen(String message) {
        stompTemplate.convertAndSend("/topic/" + KafkaConstant.KAFKA_TOPIC, message);
        consumedKafkaMessages.add(message);
    }
}