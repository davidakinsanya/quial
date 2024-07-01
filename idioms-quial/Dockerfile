FROM openjdk:17-jdk-slim

WORKDIR /src
COPY . /src

ARG user=javauser
ARG group=javauser
ARG uid=1000
ARG gid=1000

RUN apt-get update
RUN apt-get install -y dos2unix
RUN dos2unix gradlew

RUN bash gradlew clean build shadowJar
WORKDIR /run

RUN groupadd -g ${gid} ${group} && useradd -u ${uid} -g ${group} -s /bin/sh ${user}
RUN chown -R ${user}:${user} /run
USER javauser

RUN cp /src/build/libs/idioms-quial-all.jar /run/Application.jar
CMD java -jar /run/Application.jar