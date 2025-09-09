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

#FROM maven:3.9.4-eclipse-temurin-17 AS build

#WORKDIR /app

#COPY pom.xml .
#COPY src ./src

#RUN mvn clean install && mvn compile

#FROM eclipse-temurin:17-jdk
#WORKDIR /app

#COPY --from=build /app/target/*.jar app.jar

#EXPOSE 8080

#ENTRYPOINT ["java", "-jar", "app.jar"]
