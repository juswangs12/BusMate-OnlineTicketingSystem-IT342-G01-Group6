# ---------- FRONTEND BUILD ----------
FROM node:20-alpine AS frontend-build
WORKDIR /app/web
COPY web/ ./
RUN npm ci --legacy-peer-deps
RUN npm run build

# ---------- BACKEND BUILD ----------
FROM maven:3.9.6-eclipse-temurin-21-alpine AS backend-build
WORKDIR /app/backend
COPY backend/ ./
COPY --from=frontend-build /app/web/dist ./src/main/resources/static
RUN mvn clean package -DskipTests -B

# ---------- RUNTIME ----------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=backend-build /app/backend/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]