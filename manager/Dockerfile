FROM openjdk:11.0.7-jre-slim-buster

WORKDIR /app

COPY ./target/*.jar ./manager.jar

ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo:27017/springmongo","-Djava.security.egd=file:/dev/./urandom","-jar","/app/manager.jar"]

EXPOSE 8080