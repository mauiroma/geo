#FROM sdpsvalmbf.rete.testposte:5000/openshift/ubi:latest
FROM node:8.6

USER root
WORKDIR /src
COPY ./ /src/
RUN chmod -R g=u /src

EXPOSE 8080
CMD [ "./bin/http-server ./public/ -p 8080" ]

