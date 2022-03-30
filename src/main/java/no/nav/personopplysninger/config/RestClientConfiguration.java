package no.nav.personopplysninger.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ext.ContextResolver;

@Configuration
public class RestClientConfiguration {

    public static final ObjectMapper applicationObjectMapper = new ObjectMapper()
            .registerModule(new KotlinModule.Builder().build())
            .registerModule(new JavaTimeModule())
            .enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Bean
    public ContextResolver<ObjectMapper> clientObjectMapperResolver() {
        return type -> applicationObjectMapper;
    }

}
