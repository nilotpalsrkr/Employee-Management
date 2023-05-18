# Use a base image with Java 11 installed
FROM openjdk:17-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file built by Maven to the container
COPY target/*.jar employee-project-ms.jar

# Expose the port that the service will listen on
EXPOSE 8080

# Define the command to run when the container starts
CMD ["java", "-jar", "employee-project-ms.jar"]
