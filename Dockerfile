FROM eclipse-temurin:17-jdk-focal
ADD target/corporate-settlemen-0.0.1-SNAPSHOT.jar corporate-settlemen-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "corporate-settlemen-0.0.1-SNAPSHOT.jar"]