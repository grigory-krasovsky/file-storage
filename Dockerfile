# Build stage (Maven)
FROM maven:3.8.2-openjdk-17-slim AS build
WORKDIR /app

# Copy POM first for dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src
RUN mvn package -DskipTests

# Runtime stage (Lightweight JRE)
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Set environment variable
ENV ROOT_PATH=/storage

# Create the directory
RUN mkdir -p ${ROOT_PATH}

COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]