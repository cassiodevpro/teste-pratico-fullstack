# Dockerfile para Spring Boot (Java 17)
FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /app
COPY backend/pom.xml backend/mvnw ./
COPY backend/.mvn .mvn
COPY backend/src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
