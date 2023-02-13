FROM openjdk:latest
COPY build/libs/*OT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]