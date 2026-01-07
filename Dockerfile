# Use official OpenJDK 17 runtime as base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the built jar from target folder into container
COPY target/austinteck-0.0.1-SNAPSHOT.jar app.jar

# Expose the port Spring Boot runs on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Docker run command: docker run --name library-mysql \
                      #    -e MYSQL_DATABASE=library_db \
                      #    -p 3306:3306 \
                      #    -d mysql:8/0