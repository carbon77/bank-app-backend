FROM eclipse-temurin:17-jdk-alpine

VOLUME /tmp

COPY build/libs/*.jar bank-app.jar

ENTRYPOINT ["java", "-jar", "/bank-app.jar"]
