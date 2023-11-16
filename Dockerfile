FROM openjdk:17-alpine

WORKDIR /app

# Copy the application JAR file
COPY build/libs/*.jar app.jar

# Copy the application.properties file
COPY ./src/main/resources/application.properties ./src/main/resources/application.properties

# Set the timezone
ENV TZ=Asia/Seoul

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

