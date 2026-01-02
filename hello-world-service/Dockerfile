# Build stage
FROM gradle:8-jdk21-alpine AS build

WORKDIR /app

# Copy gradle files
COPY build.gradle ./

# Download dependencies (for better caching)
RUN gradle dependencies --no-daemon

# Copy source code
COPY src ./src

# Build the application
RUN gradle build --no-daemon -x test

# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built jar
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]
