# Build stage
FROM gradle:jdk11 AS build
COPY ./ /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build -x test

# Runtime stage
FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine-slim

# Application's port
EXPOSE 8080

# Debug port
EXPOSE 8382

# Copy jar from the build stage
COPY --from=build /home/gradle/src/build/libs/*.jar /app.jar

# JVM options for debugging
ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8382"

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
