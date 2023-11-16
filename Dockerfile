FROM openjdk:17-alpine

ARG JAR_FILE=build/libs/app.jar
COPY ${JAR_FILE} ./app.jar
COPY src/main/resources/application.properties ./application.properties
ENV TZ=Asia/Seoul

ENTRYPOINT ["java","-jar","./app.jar"]
