FROM navikt/java:11
COPY target/personopplysninger-api.jar app.jar
ENV APPD_ENABLED=true
ENV APP_LOG_HOME="/tmp"
ENV contextName="personopplysninger-api"
ENV JAVA_OPTS="${JAVA_OPTS} -Xms768m -Xmx1024m"
EXPOSE 8080
