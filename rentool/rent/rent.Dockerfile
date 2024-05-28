FROM openjdk:21
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} rent.jar
ENTRYPOINT ["java","-jar","/rent.jar"]
