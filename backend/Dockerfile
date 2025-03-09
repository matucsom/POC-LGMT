FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

COPY . .

#Jar para el precompile
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/*.jar backend-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "backend-app.jar"]