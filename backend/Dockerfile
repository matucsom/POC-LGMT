# -----------------------------
# Etapa 1: Construcción del JAR
# -----------------------------
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# -------------------------------------
# Etapa 2: Imagen final con OpenTelemetry
# -------------------------------------
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/*.jar backend-app.jar

# Descargar automáticamente el agente de OpenTelemetry
RUN apt-get update && apt-get install -y curl && \
    curl -L -o /opentelemetry-javaagent.jar \
    https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar && \
    apt-get remove -y curl && apt-get autoremove -y && rm -rf /var/lib/apt/lists/*

EXPOSE 8080

ENV OTEL_SERVICE_NAME=note-app
ENV OTEL_EXPORTER_OTLP_ENDPOINT=http://otel-collector:4318
ENV OTEL_TRACES_EXPORTER=otlp
ENV OTEL_METRICS_EXPORTER=none
ENV OTEL_LOGS_EXPORTER=otlp

ENTRYPOINT ["java", "-javaagent:/opentelemetry-javaagent.jar", "-jar", "backend-app.jar"]
