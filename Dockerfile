
FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY client/pom.xml ./client/
COPY common/pom.xml ./common/
COPY server/pom.xml ./server/

COPY . .

RUN mvn clean install assembly:single -pl client -am -B

FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S appgroup && \
    adduser -S appuser -G appgroup

WORKDIR /app
USER appuser

COPY --from=builder /app/client/target/client-api-1.0.0-jar-with-dependencies.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["localhost", "1488"]