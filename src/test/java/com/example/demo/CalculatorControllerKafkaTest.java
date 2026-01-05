package com.example.demo;

import com.example.demo.CalculatorApplication;
import com.example.demo.event.CalculatorEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = CalculatorApplication.class)
@AutoConfigureMockMvc
@Testcontainers
@Import(TestKafkaConfiguration.class)
@DirtiesContext
class CalculatorControllerKafkaTest {

    @Container
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"));

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAddOperationWithKafka() throws Exception {
        mockMvc.perform(get("/add")
                .param("a", "5")
                .param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("ADD"))
                .andExpect(jsonPath("$.operand1").value(5))
                .andExpect(jsonPath("$.operand2").value(3))
                .andExpect(jsonPath("$.result").value(8))
                .andExpect(jsonPath("$.status").value("PROCESSED"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testSubtractOperationWithKafka() throws Exception {
        mockMvc.perform(get("/subtract")
                .param("a", "10")
                .param("b", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("SUBTRACT"))
                .andExpect(jsonPath("$.operand1").value(10))
                .andExpect(jsonPath("$.operand2").value(4))
                .andExpect(jsonPath("$.result").value(6))
                .andExpect(jsonPath("$.status").value("PROCESSED"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testAddWithNegativeNumbers() throws Exception {
        mockMvc.perform(get("/add")
                .param("a", "-5")
                .param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(-2));
    }

    @Test
    void testSubtractWithNegativeNumbers() throws Exception {
        mockMvc.perform(get("/subtract")
                .param("a", "5")
                .param("b", "-3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(8));
    }
}
