FROM navikt/java:8

ADD ./VERSION /app/VERSION
COPY ./target/personopplysninger-api.jar "/app/app.jar"
ENV APPD_ENABLED=true
COPY appdynamics.sh /init-scripts/
