package no.nav.personopplysninger.api;

import no.nav.personopplysninger.config.ApplicationConfig;
import no.nav.security.oidc.configuration.OIDCResourceRetriever;
import no.nav.security.oidc.test.support.FileResourceRetriever;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@Import({ApplicationConfig.class})
public class TestLauncher {

    public static void main(String... args) {
        setTestEnvironment();
        ArrayList<String> argList = new ArrayList<>(Arrays.asList(args));
        argList.add("--spring.profiles.active=dev");
        SpringApplication.run(ApplicationConfig.class, argList.toArray(new String[0]));
    }

    /**
     * To be able to ovverride the oidc validation properties in
     * EnableOIDCTokenValidationConfiguration in oidc-spring-support
     */
    @Bean
    @Primary
    OIDCResourceRetriever overrideOidcResourceRetriever(){
        return new FileResourceRetriever("/metadata.json", "/jwkset.json");
    }

    @Bean
    @Primary
    public ResourceConfig testJerseyConfig() {
        return new TestRestResourceConfiguration();
    }

    private static void setTestEnvironment() {
        System.setProperty("PERSONOPPLYSNINGER_API_KODEVERK_REST_API_APIKEY_USERNAME", "");
        System.setProperty("PERSONOPPLYSNINGER_API_KODEVERK_REST_API_APIKEY_PASSWORD", "");
        System.setProperty("PERSONOPPLYSNINGER_API_NORG2_API_V1_APIKEY_USERNAME", "");
        System.setProperty("PERSONOPPLYSNINGER_API_TPS_PROXY_API_V1_INNSYN_APIKEY_USERNAME", "");
        System.setProperty("PERSONOPPLYSNINGER_API_TPS_PROXY_API_V1_INNSYN_APIKEY_PASSWORD", "");
        System.setProperty("PERSONOPPLYSNINGER_API_NORG2_API_V1_APIKEY_PASSWORD", "");
        System.setProperty("PERSONOPPLYSNINGER_API_DKIF_API_APIKEY_USERNAME", "");
        System.setProperty("PERSONOPPLYSNINGER_API_DKIF_API_APIKEY_PASSWORD", "");
        System.setProperty("DKIF_API_URL", "");
        System.setProperty("KODEVERK_REST_API_URL", "");
        System.setProperty("NORG2_API_V1_URL", "");
        System.setProperty("AAD_B2C_CLIENTID_USERNAME", "");
        System.setProperty("AAD_B2C_CLIENTID_PASSWORD", "");
        System.setProperty("TPS_PROXY_API_V1_INNSYN_URL", "");
        System.setProperty("AAD_B2C_DISCOVERY_URL", "");
        System.setProperty("FASIT_ENVIRONMENT_NAME", "");
    }
}
