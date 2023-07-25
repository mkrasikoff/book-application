FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine-slim
EXPOSE 8091
ARG JAR_FILE=build/libs/user-service-stable.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]