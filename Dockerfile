FROM openjdk:17-alpine

WORKDIR /app

# Copy the application.properties file
COPY ./src/main/resources/application.properties ./application.properties

# Copy the application JAR file
COPY build/libs/*.jar app.jar

# Set the timezone
ENV TZ=Asia/Seoul

ENTRYPOINT ["java", "-jar", "app.jar"]