# Use minimal JDK 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy build files
COPY pom.xml ./
COPY mvnw ./
COPY .mvn .mvn

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build application
RUN ./mvnw package -DskipTests

# Run the app
CMD ["java", "-jar", "target/jira.jar"]
