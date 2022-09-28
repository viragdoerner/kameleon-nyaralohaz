FROM maven:3.6.3-jdk-8
LABEL maintainer="mail.dorner.virag@gmail.com"
VOLUME /kameleon-backend
ADD target/backend-0.0.1-SNAPSHOT.jar kameleon-backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/kameleon-backend.jar"]