FROM openjdk:latest
EXPOSE 8092
ARG JAR_FILE=build/libs/PostingService-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]