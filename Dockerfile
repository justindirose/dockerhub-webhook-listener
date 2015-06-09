FROM frolvlad/alpine-oraclejdk8:slim

# Boot

RUN apk add --update bash openssl && \
    wget -O /usr/bin/boot https://github.com/boot-clj/boot/releases/download/2.0.0/boot.sh && \
    chmod +x /usr/bin/boot

ENV BOOT_VERSION 2.1.0
ENV BOOT_AS_ROOT yes
ENV BOOT_JVM_OPTIONS -Xmx2g

# download & install deps, cache REPL and web deps
RUN /usr/bin/boot web -s doesnt/exist repl -e '(System/exit 0)'
RUN rm -rf target

WORKDIR /dockerhub-webhook-listener
ADD . /dockerhub-webhook-listener

EXPOSE 8080
RUN /usr/bin/boot prod
CMD /opt/java/bin/java -jar target/dockerhub-webhook-listener.jar
