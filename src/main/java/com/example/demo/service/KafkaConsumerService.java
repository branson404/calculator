package com.example.demo.service;

import com.example.demo.event.CalculatorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(
            topics = "calculator-events",
            groupId = "calculator-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeCalculatorEvent(CalculatorEvent event) {
        try {
            logger.info("Received calculator event: {}", event);
            logger.info("Operation: {}, Operand1: {}, Operand2: {}, Result: {}", 
                    event.getOperation(), 
                    event.getOperand1(), 
                    event.getOperand2(), 
                    event.getResult());
            
            // Process the event
            processEvent(event);
            
            logger.info("Event processed successfully with ID: {}", event.getId());
        } catch (Exception e) {
            logger.error("Error consuming event from Kafka: {}", event, e);
        }
    }

    private void processEvent(CalculatorEvent event) {
        // Business logic to process the calculator event
        // Can be extended to store in database, send notifications, etc.
        switch (event.getOperation().toLowerCase()) {
            case "add":
                logger.debug("Processing ADD operation: {} + {} = {}", 
                        event.getOperand1(), event.getOperand2(), event.getResult());
                break;
            case "subtract":
                logger.debug("Processing SUBTRACT operation: {} - {} = {}", 
                        event.getOperand1(), event.getOperand2(), event.getResult());
                break;
            default:
                logger.warn("Unknown operation: {}", event.getOperation());
        }
    }
}
