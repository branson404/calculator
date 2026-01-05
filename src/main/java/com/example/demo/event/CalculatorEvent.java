package com.example.demo.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class CalculatorEvent {
    private String id;
    private String operation;
    private int operand1;
    private int operand2;
    private int result;
    private LocalDateTime timestamp;
    private String status; // PENDING, PROCESSED, ERROR

    @JsonCreator
    public CalculatorEvent(
            @JsonProperty("id") String id,
            @JsonProperty("operation") String operation,
            @JsonProperty("operand1") int operand1,
            @JsonProperty("operand2") int operand2,
            @JsonProperty("result") int result,
            @JsonProperty("timestamp") LocalDateTime timestamp,
            @JsonProperty("status") String status) {
        this.id = id;
        this.operation = operation;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.result = result;
        this.timestamp = timestamp;
        this.status = status;
    }

    public CalculatorEvent() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getOperand1() {
        return operand1;
    }

    public void setOperand1(int operand1) {
        this.operand1 = operand1;
    }

    public int getOperand2() {
        return operand2;
    }

    public void setOperand2(int operand2) {
        this.operand2 = operand2;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CalculatorEvent{" +
                "id='" + id + '\'' +
                ", operation='" + operation + '\'' +
                ", operand1=" + operand1 +
                ", operand2=" + operand2 +
                ", result=" + result +
                ", timestamp=" + timestamp +
                ", status='" + status + '\'' +
                '}';
    }
}
