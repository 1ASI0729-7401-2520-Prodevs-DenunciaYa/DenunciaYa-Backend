# Step 1: Build the application using Maven
FROM maven:3.9.11-eclipse-temurin-25 AS build
ENV SPRING_PROFILES_ACTIVE=prod

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

# Step 2: Runtime image
FROM eclipse-temurin:25-jre AS runtime

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
