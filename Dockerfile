FROM openjdk:23-ea-21-jdk-slim-bullseye
COPY /app/build/libs/KafkaProject-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "/app.jar"]
