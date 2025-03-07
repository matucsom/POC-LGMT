FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar backend-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "backend-app.jar"]