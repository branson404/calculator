#!/bin/bash

echo "Starting Calculator App with Kafka Integration (KRaft Mode)"
echo "=========================================================="

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "Docker is not running. Please start Docker first."
    exit 1
fi

# Start Kafka infrastructure (KRaft mode - no Zookeeper needed)
echo "Starting Kafka in KRaft mode..."
docker-compose up -d

# Wait for Kafka to be ready
echo "Waiting for Kafka to be ready..."
sleep 30

# Build and run the application
echo "Building and starting the application..."
./mvnw spring-boot:run