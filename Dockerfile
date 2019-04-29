FROM openjdk:8

MAINTAINER Lukasz Franczuk <l.franczuk@be-tse.com>

WORKDIR /application
COPY target/timesheet-service.jar /application/app.jar

CMD ["/bin/sh", "-c", "java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5555 -jar /application/app.jar"]

