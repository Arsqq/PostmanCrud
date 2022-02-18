FROM openjdk:11
ADD target/CasualCrudProject-0.0.1-SNAPSHOT.jar CasualCrudProject.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar", "CasualCrudProject.jar"]