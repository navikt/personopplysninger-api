FROM navikt/java:17
COPY build/libs/personopplysninger-api.jar app.jar
ENV APPD_ENABLED=true
ENV APP_LOG_HOME="/tmp"
ENV contextName="personopplysninger-api"
EXPOSE 8080
