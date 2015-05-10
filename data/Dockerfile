##############################################
#                                            #
#              ~ SimpleRPG ~                 #
# Author: Matthew Cross                      #
# Email:  blacklightgfx@gmail.com            #
#                                            #
##############################################

FROM ubuntu:14.04

EXPOSE 8080

ADD . /data

RUN apt-get update -y

RUN apt-get install -y default-jre

CMD ["java", "-jar", "/data/simplerpg.jar"]
