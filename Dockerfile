FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY . .
# Check what files are present
RUN ls -la
RUN ls -la src/
# Try building
RUN mvn clean compile -DskipTests
RUN mvn package -DskipTests
# Check if JAR was created
RUN find . -name "*.jar"
