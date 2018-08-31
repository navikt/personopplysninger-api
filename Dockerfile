FROM navikt/java:8

ADD ./VERSION /app/VERSION
COPY ./target/personopplysninger-api.jar "/app/app.jar"