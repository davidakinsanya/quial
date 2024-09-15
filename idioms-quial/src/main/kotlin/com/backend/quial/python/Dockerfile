FROM ubuntu:20.04

RUN  apt-get update \
  && apt-get install -y wget \
  && rm -rf /var/lib/apt/lists/*

RUN apt-get update
RUN apt-get install -y python3.8 python3-pip
RUN apt-get update
RUN apt-get install -y gnupg2 gnupg gnupg1
RUN apt-get update
RUN apt-get -y install curl
RUN apt-get update
RUN apt-get upgrade -y
RUN apt install sudo

ENV DEBIAN_FRONTEND noninteractive

# install google chrome
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
RUN sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list'
RUN apt-get -y update
RUN apt-get install -y google-chrome-stable
RUN sudo apt update
RUN sudo apt install google-chrome-stable
# install chromedriver
RUN apt-get install -yqq unzip
RUN wget -O /tmp/chromedriver.zip http://chromedriver.storage.googleapis.com/114.0.5735.90/chromedriver_linux64.zip
RUN unzip /tmp/chromedriver.zip chromedriver -d /usr/local/bin/

# install cron
RUN apt-get install -y cron
RUN apt-get update

# Sets the working directory in the container
WORKDIR /app

# Copies the dependency files to the working directory
COPY requirements.txt /app/requirements.txt

# Install dependencies
RUN pip3 --no-cache-dir install -r requirements.txt

# Copies everything to the working directory
COPY . /app

# Add crontab file
COPY crontab /etc/cron.d/crontab

# Give execution rights on the cron job
RUN chmod 0644 /etc/cron.d/crontab

# Create the log file to be able to run tail
RUN touch /var/log/cron.log

# Give execution rights on the cron log file
RUN chmod 0644 /var/log/cron.log

# Start the cron service
CMD cron && tail -f /var/log/cron.log