package com.example.demo;

import com.example.demo.event.CalculatorEvent;
import com.example.demo.service.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class CalculatorController {
    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);
    private final KafkaProducerService kafkaProducerService;

    public CalculatorController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/add")
    public ResponseEntity<CalculatorEvent> add(@RequestParam int a, @RequestParam int b) {
        try {
            int result = a + b;
            CalculatorEvent event = new CalculatorEvent();
            event.setId(UUID.randomUUID().toString());
            event.setOperation("ADD");
            event.setOperand1(a);
            event.setOperand2(b);
            event.setResult(result);
            event.setTimestamp(LocalDateTime.now());
            event.setStatus("PROCESSED");

            logger.info("Calculator request received: {} + {} = {}", a, b, result);
            kafkaProducerService.sendCalculatorEvent(event);

            return ResponseEntity.ok(event);
        } catch (Exception e) {
            logger.error("Error processing add request: {} + {}", a, b, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/subtract")
    public ResponseEntity<CalculatorEvent> subtract(@RequestParam int a, @RequestParam int b) {
        try {
            int result = a - b;
            CalculatorEvent event = new CalculatorEvent();
            event.setId(UUID.randomUUID().toString());
            event.setOperation("SUBTRACT");
            event.setOperand1(a);
            event.setOperand2(b);
            event.setResult(result);
            event.setTimestamp(LocalDateTime.now());
            event.setStatus("PROCESSED");

            logger.info("Calculator request received: {} - {} = {}", a, b, result);
            kafkaProducerService.sendCalculatorEvent(event);

            return ResponseEntity.ok(event);
        } catch (Exception e) {
            logger.error("Error processing subtract request: {} - {}", a, b, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
