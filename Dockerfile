# Use an OpenJDK base image
# Use a different OpenJDK base image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/notification-service-0.0.1-SNAPSHOT.jar /app/notification-service.jar

# Expose the port the service will run on
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "notification-service.jar"]
