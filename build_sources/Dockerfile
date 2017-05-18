FROM openjdk:8-jdk

ADD ../target/demo_app.jar /opt/

EXPOSE 8080

ENTRYPOINT ["java -jar", "/opt/demo_app.jar"]
