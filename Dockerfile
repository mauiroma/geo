FROM 	    maven:3.6.0-jdk-8 AS build
ARG         MAVEN_OPTS

EXPOSE 	    8080
WORKDIR     /usr/src/app

COPY 	    pom.xml .
RUN         mvn dependency:go-offline
COPY 	    src ./src
RUN 	    mvn package

FROM 	    openjdk:8-jre-slim
WORKDIR     /usr/src/app
COPY	    --from=build /usr/src/app/target/bank-service-0.0.1-SNAPSHOT.jar bank-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT 	["java", "-jar", "bank-service-0.0.1-SNAPSHOT.jar"]

