FROM openjdk:8-jdk-alpine

ADD target/mktimer-0.0.2-SNAPSHOT.jar /app.jar

ENTRYPOINT exec java -jar /app.jar
