FROM ubuntu:latest

WORKDIR /app

COPY . /app/

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

RUN mvn clean install && mvn compile

# Make sure run.sh is executable
RUN chmod +x run.sh

EXPOSE 8080

CMD ["./run.sh"]
