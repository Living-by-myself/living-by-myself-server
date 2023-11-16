FROM openjdk:17-alpine

ARG JAR_FILE=build/libs/app.jar
COPY ${JAR_FILE} ./app.jar
ENV TZ=Asia/Seoul

# Redis 클라이언트 설치
RUN apk --no-cache add redis

ENTRYPOINT ["java","-jar","./app.jar"]
