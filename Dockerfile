FROM navikt/java:11
COPY target/personopplysninger-api.jar app.jar
#ENV APPD_ENABLED=true
ENV JAVA_OPTS="${JAVA_OPTS} -Xms768m -Xmx1024m"
#RUN chown -R 1069 /app
EXPOSE 8080
