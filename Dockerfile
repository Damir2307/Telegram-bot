FROM openjdk:8-jre-alpine
MAINTAINER Damir <damirrakhmetulla@gmail.com>

ENV TZ=Asia/Almaty
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
VOLUME /tmp
ARG JAR_FILE
ADD ./target/tbot-1.0-SNAPSHOT.jar /app/
EXPOSE 80
ENTRYPOINT ["java","-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap","-jar","/app/tbot-1.0-SNAPSHOT.jar"]