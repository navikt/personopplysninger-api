FROM navikt/java:8

ADD ./VERSION /app/VERSION
COPY ./target/personopplysninger-api.jar "/app/app.jar"
ENV FOOTER_TYPE=WITH_ALPHABET
ENV APPLICATION_NAME=personopplysninger-api
ENV CONTEXT_PATH=person/personopplysninger-api
ENV APPD_ENABLED=true
COPY 15-appdynamics.sh /init-scripts/
