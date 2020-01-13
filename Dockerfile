FROM openjdk:8
ADD target/agile-intent-api.jar agile-intent-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","agile-intent-api.jar"]