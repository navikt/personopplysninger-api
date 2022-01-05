package no.nav.personopplysninger.api;

import no.nav.personopplysninger.config.ApplicationConfig;
import no.nav.security.token.support.test.FileResourceRetriever;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@Import({ApplicationConfig.class})
public class TestLauncher {

    public static void main(String... args) {
        SpringApplication app = new SpringApplication(ApplicationConfig.class);
        app.run(args);
    }

    /**
     * To be able to ovverride the oidc validation properties in
     * EnableOIDCTokenValidationConfiguration in oidc-spring-support
     */
    @Bean
    @Primary
    FileResourceRetriever overrideOidcResourceRetriever(){
        return new FileResourceRetriever("/metadata.json", "/jwkset.json");
    }
}
