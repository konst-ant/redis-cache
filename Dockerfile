FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
EXPOSE 5005
ARG JAR_FILE=./target/redis-cache-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005","-jar","/app.jar"]
