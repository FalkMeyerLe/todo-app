FROM openjdk:19-jdk-slim
WORKDIR /app
COPY build/libs/todo-service-1.0.0.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]