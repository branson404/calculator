# Calculator Spring Boot App with Kafka Integration

This is a simple Spring Boot calculator application that demonstrates end-to-end Kafka integration.

## Features

- Basic calculator operations (addition and subtraction)
- Kafka producer that publishes calculator events
- Kafka consumer that processes calculator events
- Embedded Kafka for testing
- Docker Compose setup for local development

## Architecture

The application uses an event-driven architecture:

1. **CalculatorController**: Receives HTTP requests and publishes events to Kafka
2. **KafkaProducerService**: Sends calculator events to the `calculator-events` topic
3. **KafkaConsumerService**: Consumes and processes calculator events from the topic

## Event Flow

```
HTTP Request → Controller → Kafka Producer → Kafka Topic → Kafka Consumer → Processing
```

## Running the Application

### Prerequisites

- Java 17
- Maven
- Docker and Docker Compose (for local Kafka setup)

### Local Development with Docker

1. Start Kafka (KRaft mode):
```bash
docker-compose up -d
```

2. Run the application:
```bash
./mvnw spring-boot:run
```

3. Access the application:
- Calculator API: http://localhost:8000
- Kafka UI: http://localhost:8080

### API Endpoints

- `GET /add?a={number}&b={number}` - Add two numbers
- `GET /subtract?a={number}&b={number}` - Subtract two numbers

### Example Requests

```bash
curl "http://localhost:8000/add?a=5&b=3"
curl "http://localhost:8000/subtract?a=10&b=4"
```

### Response Format

Each request returns a JSON event object:

```json
{
  "id": "uuid",
  "operation": "ADD|SUBTRACT",
  "operand1": 5,
  "operand2": 3,
  "result": 8,
  "timestamp": "2026-01-05T06:34:07.54248458",
  "status": "PROCESSED"
}
```

## Testing

Run the tests:

```bash
./mvnw test
```

The application includes:
- Unit tests for controller endpoints
- Integration tests with embedded Kafka
- Kafka consumer verification

## Configuration

Kafka configuration is in `application.properties`:

```properties
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=calculator-group
# ... other Kafka settings
```

## Docker Setup

The `docker-compose.yml` includes:
- Kafka broker running in KRaft mode (no Zookeeper needed)
- Kafka UI for monitoring

## KRaft Mode

This setup uses Kafka's KRaft (Kafka Raft) consensus protocol, which eliminates the need for Apache Zookeeper. The Kafka broker acts as both the broker and controller, providing a simpler and more reliable deployment.

## Monitoring

- Application logs show Kafka producer/consumer activity
- Kafka UI provides topic monitoring and message inspection
- All calculator events are logged and processed asynchronously