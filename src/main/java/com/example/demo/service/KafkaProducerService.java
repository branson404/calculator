package com.example.demo.service;

import com.example.demo.event.CalculatorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final String TOPIC = "calculator-events";

    private final KafkaTemplate<String, CalculatorEvent> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, CalculatorEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCalculatorEvent(CalculatorEvent event) {
        try {
            logger.info("Publishing calculator event: {}", event);
            
            Message<CalculatorEvent> message = MessageBuilder
                    .withPayload(event)
                    .setHeader(KafkaHeaders.TOPIC, TOPIC)
                    .setHeader("kafka_messageKey", event.getId())
                    .build();

            kafkaTemplate.send(message);
            
            logger.info("Event published successfully with ID: {}", event.getId());
        } catch (Exception e) {
            logger.error("Error sending event to Kafka: {}", event, e);
            throw new RuntimeException("Failed to send event to Kafka", e);
        }
    }
}
