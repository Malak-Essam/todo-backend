# Use a lightweight JDK image
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy project files
COPY . .

# Build your app
RUN ./mvnw -DskipTests package

# Run the generated JAR
CMD ["java", "-jar", "target/*.jar"]
