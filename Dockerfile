FROM openjdk:17 AS builder
# Add Maintainer Info
LABEL maintainer="jiaoyuyang <jiaoyuyang@live.com>"
WORKDIR workspace
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} edge-service.jar
RUN java -Djarmode=layertools -jar edge-service.jar extract

FROM openjdk:17
WORKDIR workspace
COPY --from=builder workspace/dependencies/ ./
COPY --from=builder workspace/spring-boot-loader/ ./
COPY --from=builder workspace/snapshot-dependencies/ ./
COPY --from=builder workspace/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]