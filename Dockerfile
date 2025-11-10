FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
ARG JAR_FILE=projeto_nuvem-0.0.1-SNAPSHOT.jar
COPY target/${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]