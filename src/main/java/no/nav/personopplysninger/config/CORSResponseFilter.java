package no.nav.personopplysninger.config;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.util.List;

import static java.util.Arrays.asList;

@Provider
public class CORSResponseFilter implements ContainerResponseFilter {

    private static final List<String> ALLOWED_ORIGINS = asList(
            "http://localhost:8080",
            "https://personopplysninger-t6.nais.oera-q.local",
            "https://personopplysninger-q6.nais.oera-q.local",
            "https://personopplysninger-q4.nais.oera-q.local",
            "https://personopplysninger-q1.nais.oera-q.local",
            "https://personopplysninger-q0.nais.oera-q.local",
            "https://personopplysninger-q.nav.no",
            "https://tjenester-t6.nav.no",
            "https://tjenester-q6.nav.no",
            "https://tjenester-q4.nav.no",
            "https://tjenester-q1.nav.no",
            "https://tjenester-q0.nav.no",
            "https://person-q.nav.no",
            "https://person.nav.no",
            "https://www-q0.nav.no",
            "https://www-q1.nav.no",
            "https://www.nav.no"
    );

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) {
        String origin = request.getHeaderString("Origin");
        if (ALLOWED_ORIGINS.contains(origin)) {
            response.getHeaders().add("Access-Control-Allow-Origin", origin);
            response.getHeaders().add("Access-Control-Allow-Headers",
                    "origin, content-type, accept, authorization");
            response.getHeaders().add("Access-Control-Allow-Credentials", "true");
            response.getHeaders().add("Access-Control-Allow-Methods",
                    "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        }
    }
}
