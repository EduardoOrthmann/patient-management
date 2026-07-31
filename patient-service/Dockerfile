FROM maven:3.9.9-eclipse-temurin-23 AS build
WORKDIR /app

# Copy and build the common module
COPY grpc-common/pom.xml ./grpc-common/
COPY grpc-common/src ./grpc-common/src/
RUN mvn -f grpc-common/pom.xml clean install -DskipTests -B

# Copy and build the patient service
COPY patient-service/pom.xml ./patient-service/
COPY patient-service/src ./patient-service/src/
RUN mvn -f patient-service/pom.xml clean package -DskipTests -B

FROM eclipse-temurin:23-jre-alpine AS runtime
WORKDIR /app

# Set non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/patient-service/target/patient-service-*.jar patient-service.jar

# Expose the ports for REST endpoints
EXPOSE 4000

ENTRYPOINT ["java", "-jar", "patient-service.jar"]