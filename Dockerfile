FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

RUN groupadd --system spring && useradd --system --gid spring spring

COPY target/student-management-1.0.0.jar /app/app.jar

ENV SERVER_PORT=8080
EXPOSE 8080

USER spring

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
