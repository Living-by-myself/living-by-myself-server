FROM openjdk:17
ARG JAR_FILE=build/libs/app.jar
COPY ${JAR_FILE} ./app.jar
COPY ./application.properties /path/to/target/directory/application.properties
ENV TZ=Asia/Seoul
ENTRYPOINT ["java","-jar","./app.jar"]