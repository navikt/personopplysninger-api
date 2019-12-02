FROM navikt/java:8-appdynamics
COPY target/personopplysninger-api.jar app.jar
EXPOSE 8080
ENV JAVA_OPTS="${JAVA_OPTS} -Xms768m -Xmx1024m"
