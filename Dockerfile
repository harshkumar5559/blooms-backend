FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy
COPY --from=build /target/*.jar blooms.jar
EXPOSE 8092
ENTRYPOINT ["java","-jar","blooms.jar"]