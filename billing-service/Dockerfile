FROM maven:3.9.9-eclipse-temurin-23 AS build
WORKDIR /app

# Copy and build the common module
COPY grpc-common/pom.xml ./grpc-common/
COPY grpc-common/src ./grpc-common/src/
RUN mvn -f grpc-common/pom.xml clean install -DskipTests -B

# Copy and build the billing service
COPY billing-service/pom.xml ./billing-service/
COPY billing-service/src ./billing-service/src/
RUN mvn -f billing-service/pom.xml clean package -DskipTests -B

FROM eclipse-temurin:23-jre-alpine AS runtime
WORKDIR /app

# Set non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/billing-service/target/billing-service-*.jar billing-service.jar

# Expose the ports for gRPC and REST endpoints
EXPOSE 4001
EXPOSE 9001

ENTRYPOINT ["java", "-jar", "billing-service.jar"]