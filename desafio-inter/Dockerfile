FROM openjdk:11.0.7-jre-slim-buster

WORKDIR /app

COPY ./target/*.jar ./broker.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/broker.jar"]

EXPOSE 8080