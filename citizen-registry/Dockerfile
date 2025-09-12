# Stage 1: Build the Maven project
FROM maven:3.9.6-eclipse-temurin-17-alpine@sha256:53f16a14a83c0c3600ec8e18d21939996816a085b8a2699b835fedade1ed893a AS build
WORKDIR /app

# Copy only the POM file to leverage Docker caching
COPY pom.xml .

# Copy the domain model sub-project for dependency reasons
RUN mkdir domain
COPY domain/src /app/domain/src
COPY domain/pom.xml /app/domain/pom.xml

# Copy the spring boot sub-project
RUN mkdir service
COPY service/src /app/service/src
COPY service/pom.xml /app/service/pom.xml

# Package first domain model
WORKDIR /app/domain
# Download dependencies. This layer will be cached if pom.xml is not changed
RUN mvn -B dependency:go-offline
# Package without tests
RUN mvn -B clean package -DskipTests

# Package secondly the Spring Boot service
WORKDIR /app/service
# Download dependencies. This layer will be cached if pom.xml is not changed
RUN mvn -B dependency:go-offline
# Package without tests
RUN mvn -B clean package -DskipTests

# Stage 2: Create a minimal runtime image
FROM eclipse-temurin:17-alpine@sha256:b5d37df8ee5bb964bb340acca83957f9a09291d07768fba1881f6bfc8048e4f5 AS runtime

# Set a non-root user for security reasons
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Create working directory
RUN mkdir /app && chown -R appuser:appgroup /app
WORKDIR /app

# Copy the JAR file built in the previous stage
COPY --from=build --chown=appuser:appuser /app/service/target/*-SNAPSHOT.jar app.jar

# Change to non-root user
USER appuser

# Expose the port your application listens on
EXPOSE 8080

#Environment variables
ENV DB_HOST=localhost
ENV DB_NAME=citizens
ENV DB_USER=root
ENV DB_PASSWORD=root

#HEALTHCHECK
HEALTHCHECK --interval=15s --timeout=3s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1
  
# Command to run the application
ENTRYPOINT ["/opt/java/openjdk/bin/java", "-jar", "/app/app.jar"]