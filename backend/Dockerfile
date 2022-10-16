FROM maven:3.6.3-jdk-8 AS backend
RUN apt update && apt install -y mariadb-server

WORKDIR /home/app
COPY /pom.xml .
RUN mvn install

COPY /init.sql .
RUN /etc/init.d/mysql start && mysql < init.sql 

COPY /src src

RUN /etc/init.d/mysql start && mvn -f pom.xml package

EXPOSE 8080

VOLUME /var/lib/mysql

CMD /etc/init.d/mysql start && java -jar target/backend-0.0.1-SNAPSHOT.jar