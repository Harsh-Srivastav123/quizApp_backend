# Use the official Maven image as a base image
#FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory
#WORKDIR /app
#
## Copy the Maven project file
#COPY pom.xml .
#
## Download dependencies
#RUN mvn dependency:go-offline
#
## Copy the source code
#COPY src ./src
#
## Build the application
#RUN mvn package

# Use the official OpenJDK 17 image as a base image
FROM openjdk:17-slim

## Set the working directory
#WORKDIR /app

# Copy the JAR file from the build image
#COPY --from=build /app/target/your-application-name.jar ./app.jar
ADD target/QuizApp-0.0.1-SNAPSHOT.jar QuizApp-0.0.1-SNAPSHOT.jar
# Expose port 8080


VOLUME /tmp

# Run the application
ENTRYPOINT ["java", "-jar", "QuizApp-0.0.1-SNAPSHOT.jar"]