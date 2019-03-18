FROM openjdk:8

MAINTAINER Lukasz Franczuk <l.franczuk@be-tse.com>

WORKDIR /application
COPY target/timesheet-service.jar /application/app.jar

CMD ["/bin/sh", "-c", "java -jar /application/app.jar"]
