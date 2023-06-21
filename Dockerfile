FROM maven:3.8.4-openjdk-8-slim
LABEL authors="vmoli"

WORKDIR /app
COPY pom.xml .

RUN mvn dependency:go-offline
COPY src/ ./src/

RUN mvn package

ENTRYPOINT ["mvn", "exec:java", "-Dexec.mainClass=br.edu.ufabc.napster.server.Server", "-Dexec.keepAlive=true"]