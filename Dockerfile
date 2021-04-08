FROM openjdk:11-jre-slim-buster
ADD target/turkcellpoc.jar turkcellpoc.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","turkcellpoc.jar"]