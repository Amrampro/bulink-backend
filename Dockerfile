# Dockerfile for user-microservice

FROM adoptopenjdk:21-jre-hotspot

WORKDIR /app

COPY target/user-microservice-0.0.1-SNAPSHOT.jar /app/user-microservice.jar

EXPOSE 8081

CMD ["java", "-jar", "user-microservice.jar"]