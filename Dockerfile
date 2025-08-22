FROM ubuntu 

WORKIR /app

COPY . /app/

RUN apt-get update &&\
    apt-get install -y openjdk-17-jdk maven

RUN maven clean install && maven compile

RUN chmod +x run.sh

EXPOSE 8000

CMD ["./run.sh"]
