FROM openjdk:8-jdk-alpine
ADD /target/agile-intent-api.jar agile-intent-api.jar
EXPOSE 8080
ENTRYPOINT [ "sh","-c","java -jar /agile-intent-api.jar"]