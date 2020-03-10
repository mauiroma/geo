FROM sdpsvalmbf.rete.testposte:5000/openshift/ubi:latest
USER root
WORKDIR /newsletter
#COPY ./edit.html edit.html
#COPY ./view.html view.html
#COPY ./error.html error.html
#COPY ./root.txt root.txt
#COPY ./wiki /newsletter/wiki
COPY ./ /newsletter

RUN chmod -R g=u /newsletter

EXPOSE 8080
#ENTRYPOINT [ "./wiki" ]
ENTRYPOINT [ "./server" ]

