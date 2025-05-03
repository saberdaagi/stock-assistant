# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy the entire project
COPY . .

# Build all modules
RUN mvn clean install -DskipTests

# Stock Server stage
FROM eclipse-temurin:21-jre AS stock-server
WORKDIR /app
COPY --from=builder /app/stock-server/stock-server-starter/target/stock-server-starter-*.jar app.jar
ENV JAVA_OPTS="-Xmx512m -Xms256m"
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

# AI Client stage
FROM eclipse-temurin:21-jre AS stock-ai-client
WORKDIR /app
COPY --from=builder /app/stock-ai-client/target/stock-ai-client-*.jar app.jar
ENV JAVA_OPTS="-Xmx512m -Xms256m"
EXPOSE 8060
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 