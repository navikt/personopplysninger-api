FROM gcr.io/distroless/java21-debian12
COPY build/libs/personopplysninger-api-all.jar /app/app.jar
ENV PORT=8080
EXPOSE $PORT
WORKDIR app
CMD ["app.jar"]