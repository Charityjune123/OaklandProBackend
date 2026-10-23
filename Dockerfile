# Stage 1: Build the application using Maven wrapper
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copy dependency and source files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Give execution permission to Maven wrapper and build package
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final lightweight runtime image
FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp

# Copy the built jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Spring Boot default port
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app.jar"]