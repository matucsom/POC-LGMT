# -----------------------------
# Etapa 1: Construcción del JAR
# -----------------------------
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

COPY . .

# Compila y genera el JAR de tu aplicación
RUN mvn clean package -DskipTests


# -------------------------------------
# Etapa 2: Imagen final con OpenTelemetry
# -------------------------------------
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiamos el JAR compilado desde la etapa builder
COPY --from=builder /app/target/*.jar backend-app.jar

# 👇 Descargar automáticamente el agente de OpenTelemetry
RUN apt-get update && apt-get install -y curl && \
    curl -L -o /opentelemetry-javaagent.jar \
    https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar && \
    apt-get remove -y curl && apt-get autoremove -y && rm -rf /var/lib/apt/lists/*

# Exponemos el puerto
EXPOSE 8080

# Variables de entorno para el agente
ENV OTEL_SERVICE_NAME=note-app
ENV OTEL_EXPORTER_OTLP_ENDPOINT=http://otel-collector:4318
ENV OTEL_METRICS_EXPORTER=none
ENV OTEL_LOGS_EXPORTER=none

# Iniciamos la app con el agente activo
ENTRYPOINT ["java", "-javaagent:/opentelemetry-javaagent.jar", "-jar", "backend-app.jar"]
