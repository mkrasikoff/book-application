FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine-slim

# Application's port
EXPOSE 8080

# Debug port
EXPOSE 8382

ARG JAR_FILE=build/libs/user-service-stable.jar
COPY ${JAR_FILE} /app.jar

# JVM options for debugging
ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8382"

ENTRYPOINT ["java", "-jar", "app.jar"]
