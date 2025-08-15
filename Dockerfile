# Use a multi-stage build for smaller images
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/twistreview-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

ENV SPRING_CONFIG_LOCATION=file:/app/Application.yaml

# Set environment variables (can be overridden at runtime)
ENV MONGO_HOST=192.168.10.43
ENV MONGO_PORT=27017
ENV MONGO_USERNAME=jeff
ENV MONGO_PASSWORD=mypasssword


ENTRYPOINT ["java", "-jar", "app.jar"]
