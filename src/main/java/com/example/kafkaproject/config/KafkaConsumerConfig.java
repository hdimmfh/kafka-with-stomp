package com.example.kafkaproject.config;

import com.example.kafkaproject.constant.KafkaConstant;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.lang.NonNull;

import java.util.*;


@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    @Bean
    @Primary
    public ArrayList<String> consumedKafkaMessages(){
        // Server session messages
        return new ArrayList<String>();
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        // Factory for KafkaListener(@Service)
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        ContainerProperties prop = factory.getContainerProperties();
        prop.setConsumerRebalanceListener(rebalancedListener());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        // Kafka configuration
        Map<String,Object> config = new HashMap<>();

        // To handle exceptions separately after JSON parsing, I have initially set the deserializer to String.
        StringDeserializer deserializer = new StringDeserializer();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstant.KAFKA_BROKER);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put("group.id", KafkaConstant.GROUP_ID);

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConsumerAwareRebalanceListener rebalancedListener() {
        // Re-balance listener with kafka partitions.
        return new ConsumerAwareRebalanceListener() {
            @Override
            public void onPartitionsAssigned(@NonNull Consumer<?, ?> consumer,@NonNull Collection<TopicPartition> partitions) {
                List<Integer> partList = new ArrayList<>();
                for (TopicPartition partition : partitions) {
                    int partition1 = partition.partition();
                    partList.add(partition1);
                }
                KafkaConstant.partitionList = partList;
            }
        };
    }

}