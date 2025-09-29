FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

# Use Maven directly instead of mvnw
RUN apt-get update && apt-get install -y maven

RUN mvn clean package -DskipTests

CMD ["java", "-jar", "target/*.jar"]