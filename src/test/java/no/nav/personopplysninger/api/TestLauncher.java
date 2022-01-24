package no.nav.personopplysninger.api;

import no.nav.personopplysninger.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@Import({ApplicationConfig.class})
public class TestLauncher {

    public static void main(String... args) {
        SpringApplication app = new SpringApplication(ApplicationConfig.class);
        app.run(args);
    }
}
