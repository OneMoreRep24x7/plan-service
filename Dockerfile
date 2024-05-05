FROM openjdk:17
EXPOSE 8082
ADD target/planservice-onemorerep.jar planservice-onemorerep.jar
ENTRYPOINT ["java","-jar","/planservice-onemorerep.jar"]