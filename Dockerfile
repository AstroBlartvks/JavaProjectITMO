
FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /app
COPY . .

RUN mvn clean install assembly:single -pl server -am -B
FROM eclipse-temurin:17-jre-alpine

RUN addgroup --system --gid 1001 appgroup && \
    adduser --system --uid 1001 --ingroup appgroup appuser

RUN wget -O /bin/grpc_health_probe https://github.com/grpc-ecosystem/grpc-health-probe/releases/download/v0.4.14/grpc_health_probe-linux-amd64 \
    && chmod +x /bin/grpc_health_probe

WORKDIR /app
RUN mkdir -p applogs && chown appuser:appgroup applogs

USER appuser

COPY --from=builder /app/server/target/server-api-1.0.0-jar-with-dependencies.jar ./app.jar
COPY --chown=appuser:appgroup server/src/main/resources/log4j2.xml .

EXPOSE 3000 1488 5432

ENTRYPOINT ["java", "-jar", "app.jar"]
CMD []