# Use AdoptOpenJDK's OpenJDK 11 with Alpine Linux as the base image
FROM adoptopenjdk:17-jre-hotspot-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/sda-catalogue-rest-api.jar /app/sda-catalogue-rest-api.jar

# Expose the port that your Spring Boot application will run on
EXPOSE 8080

# Command to run your application
CMD ["java", "-jar", "your-spring-boot-app.jar"]
