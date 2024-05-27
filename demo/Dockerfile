FROM openjdk:11-jre-slim
COPY ./build/libs/*.jar servicea.jar
ENTRYPOINT ["java","-jar","/servicea.jar"]