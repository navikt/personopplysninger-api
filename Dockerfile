FROM navikt/java:8-appdynamics
COPY target/personopplysninger-api.jar app.jar
ENV JAVA_OPTS="${JAVA_OPTS} -Xms768m -Xmx1024m"
ENV APPD_ENABLED=true
EXPOSE 8080
