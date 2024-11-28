FROM gcr.io/distroless/java21-debian12
COPY build/libs/personopplysninger-api-all.jar /app/app.jar
WORKDIR app
CMD ["app.jar"]