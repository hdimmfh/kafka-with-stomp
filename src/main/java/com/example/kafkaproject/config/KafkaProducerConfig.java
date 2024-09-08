package com.example.kafkaproject.config;

import com.example.kafkaproject.constant.KafkaConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfiguration());
    }

    @Bean
    public Map<String,Object> kafkaProducerConfiguration() {
        // Kafka configuration
        Map<String,Object> config = new HashMap<>();

        config.put("bootstrap.servers", KafkaConstant.KAFKA_BROKER);
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("acks", "all");
        config.put("block.on.buffer.full", "true");

        return config;
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        // Kafka message producing template
        return new KafkaTemplate<>(producerFactory());
    }

}