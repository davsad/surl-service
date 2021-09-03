FROM openjdk:11-jre-slim

ARG JETTY_DIR=/app/jetty

USER root
ENV JETTY_HOME=/app/jetty
ENV JETTY_BASE=$JETTY_HOME
ENV JETTY_USER=jetty
ENV JETTY_GROUP=jetty

RUN useradd -m jetty
RUN groupadd -f jetty
RUN usermod -a -G jetty jetty
RUN mkdir -p $JETTY_DIR
RUN chown jetty:jetty $JETTY_DIR
RUN  apt-get update \
		&& apt-get install -y wget
USER jetty
ENV JETTY_HOME=/app/jetty
#Download jetty
RUN wget https://repo1.maven.org/maven2/org/eclipse/jetty/jetty-distribution/9.4.31.v20200723/jetty-distribution-9.4.31.v20200723.tar.gz -O $JETTY_DIR/jetty.tar.gz && \
        tar xvz -C $JETTY_DIR -f $JETTY_DIR/jetty.tar.gz --strip 1 && \
        rm -rf $JETTY_DIR/jetty.tar.gz

COPY --chown=jetty:jetty ./target/surl-service.war $JETTY_DIR/webapps/root.war
COPY --chown=jetty:jetty ./scripts/startup.sh $JETTY_DIR/
RUN chmod 770 $JETTY_DIR/startup.sh

ENTRYPOINT ["sh", "-c", "$JETTY_HOME/startup.sh"]
