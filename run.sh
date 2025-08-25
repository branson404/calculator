#!/bin/bash

# Find the JAR file inside target/
JAR_FILE=$(find target -name "*.jar" | head -n 1)

# Run the JAR if found
if [ -n "$JAR_FILE" ]; then
    echo "Running $JAR_FILE"
    java -jar "$JAR_FILE"
else
    echo "JAR file not found!"
    exit 1
fi

