FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /app

# Create proper Maven structure
RUN mkdir -p src/main/java
RUN mkdir -p src/main/resources

# Copy Java files to correct location
COPY *.java src/main/java/
COPY application.properties src/main/resources/
COPY college_data.json src/main/resources/
COPY chatbot.html src/main/resources/
COPY pom.xml .

# Build the application
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
