#!/bin/sh

export JAVA_OPTIONS="$JAVA_OPTIONS -Xms1024m -Xmx2058m"

cd $JETTY_HOME
java -jar $JAVA_OPTIONS "$JETTY_HOME/start.jar"