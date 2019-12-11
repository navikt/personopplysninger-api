FROM navikt/java:8-appdynamics
COPY target/personopplysninger-api.jar app.jar
ENV APPD_ENABLED=true
EXPOSE 8080
