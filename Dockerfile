FROM navikt/java:8

ADD ./VERSION /app/VERSION
COPY ./target/personopplysninger-backend.jar "/app/app.jar"