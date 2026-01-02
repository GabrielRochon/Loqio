# Build stage
FROM gradle:8-jdk21-alpine AS build

WORKDIR /app

# Copy gradle files and checkstyle config
COPY build.gradle ./
COPY ../config/checkstyle/checkstyle.xml ../config/checkstyle/checkstyle.xml

# Download dependencies (for better caching)
RUN gradle dependencies --no-daemon

# Copy source code
COPY language-content-service/src ./src

# Build the application
RUN gradle build --no-daemon -x test

# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built jar
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8082

CMD ["java", "-jar", "app.jar"]
