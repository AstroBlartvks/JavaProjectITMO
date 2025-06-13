
FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY common/pom.xml ./common/
COPY server/pom.xml ./server/

COPY . .

RUN mvn clean install assembly:single -pl server -am -B

FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S appgroup && \
    adduser -S appuser -G appgroup

WORKDIR /app
COPY applogs app/applogs
USER appuser

COPY --from=builder /app/server/target/server-api-1.0.0-jar-with-dependencies.jar ./app.jar
EXPOSE 5432
EXPOSE 3000
EXPOSE 1488

ENTRYPOINT ["java", "-jar", "app.jar"]
