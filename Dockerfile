FROM amazoncorretto:17
COPY target/*.jar task-manager.jar
ENTRYPOINT ["java","-jar","/task-manager.jar"]
