FROM java:8-jdk-alpine

COPY spring-boot-jpa-postgresql-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch spring-boot-jpa-postgresql-0.0.1-SNAPSHOT.jar'

EXPOSE 9090

ENTRYPOINT ["java","-jar","spring-boot-jpa-postgresql-0.0.1-SNAPSHOT.jar"]