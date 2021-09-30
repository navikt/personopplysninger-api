FROM navikt/java:11-appdynamics
RUN mkdir /app
RUN chown -R 1069 /app
COPY target/personopplysninger-api.jar app.jar
ENV APPD_ENABLED=true
ENV JAVA_OPTS="${JAVA_OPTS} -Xms768m -Xmx1024m"
EXPOSE 8080
