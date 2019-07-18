FROM navikt/java:8
COPY target/personopplysninger-api.jar app.jar
EXPOSE 9022