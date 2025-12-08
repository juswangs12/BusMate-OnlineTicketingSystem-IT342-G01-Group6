# Stage 1: Build Vite (React) frontend
FROM node:20-alpine AS frontend-build
WORKDIR /app
COPY web/ ./web
WORKDIR /app/web
RUN npm ci --legacy-peer-deps
RUN npm run build

# Stage 2: Build Spring Boot backend and embed frontend
FROM maven:3.9.6-eclipse-temurin-21-alpine AS backend-build
WORKDIR /app
COPY backend/ ./backend
COPY --from=frontend-build /app/web/dist ./backend/src/main/resources/static
WORKDIR /app/backend
RUN mvn clean package -DskipTests -B

# Stage 3: Run the app
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=backend-build /app/backend/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]