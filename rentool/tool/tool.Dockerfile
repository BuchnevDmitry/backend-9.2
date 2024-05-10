FROM openjdk:21
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} tool.jar
ENTRYPOINT ["java","-jar","/tool.jar"]
