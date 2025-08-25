FROM ubuntu 

WORKdIR /app

COPY . /app/

RUN apt-get update &&\
    apt-get install -y openjdk-17-jdk maven

RUN mvn clean install && mvn compile

RUN chmod +x run.sh

EXPOSE 8000

CMD ["./run.sh"]
