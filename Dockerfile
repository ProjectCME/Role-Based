FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:17-jre-jammy
ARG JAR_FILE=/workspace/target/*.jar
COPY --from=build ${JAR_FILE} /app/app.jar
EXPOSE 8080

RUN addgroup --system app && adduser --system --ingroup app app
USER app
ENTRYPOINT ["java","-jar","/app/app.jar"]
