FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /app
COPY pom.xml mvnw .
COPY .mvn .mvn
COPY src src
RUN ./mvnw -DskipTests package -q

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]