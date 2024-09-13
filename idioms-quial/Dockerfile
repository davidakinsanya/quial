FROM openjdk:17-jdk-slim

WORKDIR /src
COPY . /src

RUN mkdir -p /usr/src/app

ARG user=javauser
ARG group=javauser
ARG uid=1000
ARG gid=1000

RUN apt-get update
RUN apt-get install -y dos2unix
RUN dos2unix gradlew

RUN bash gradlew clean build shadowJar

RUN groupadd -g ${gid} ${group} && useradd -u ${uid} -g ${group} -s /bin/sh ${user}
RUN chown -R ${user}:${user} /run
RUN chown -R ${user}:${user} /usr/src/app

USER javauser

CMD java -jar /src/build/libs/idioms-quial-all.jar