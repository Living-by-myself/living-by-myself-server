FROM openjdk:17-alpine

WORKDIR /app

COPY ./src/main/resources/application.properties ./application.properties

COPY build/libs/*.jar app.jar

ENV TZ=Asia/Seoul

ENTRYPOINT ["java", "-jar", "app.jar"]