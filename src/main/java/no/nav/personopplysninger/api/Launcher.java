package no.nav.personopplysninger.api;

import no.nav.personopplysninger.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@EnableCaching
public class Launcher {

    public static void main(String... args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }

    @Bean
    public ConcurrentMapCache kodeverkCache() {
        return new ConcurrentMapCache("KodeverkCache");
    }

    @Bean
    public CacheManager cacheManager(ConcurrentMapCache kodeverkCache) {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("kjonn"),
                new ConcurrentMapCache("kommune"),
                new ConcurrentMapCache("land"),
                new ConcurrentMapCache("status"),
                new ConcurrentMapCache("postnr"),
                new ConcurrentMapCache("sivilstand"),
                new ConcurrentMapCache("valuta"),
                new ConcurrentMapCache("spraak"),
                new ConcurrentMapCache("statsborgerskap"),
               kodeverkCache
        ));
        return cacheManager;
    }

}
