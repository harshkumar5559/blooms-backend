FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /target/*.jar blooms.jar
EXPOSE 8091
ENTRYPOINT ["java","-jar","blooms.jar"]