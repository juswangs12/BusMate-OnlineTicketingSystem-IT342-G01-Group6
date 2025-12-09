# Stage 1: Build Spring Boot backend
FROM maven:3.9.6-eclipse-temurin-21-alpine AS backend-build
WORKDIR /app
COPY backend/ ./backend
WORKDIR /app/backend
RUN mvn clean package -DskipTests -B

# Stage 2: Run the app
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=backend-build /app/backend/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
