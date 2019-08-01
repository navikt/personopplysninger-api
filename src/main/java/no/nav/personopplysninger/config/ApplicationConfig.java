package no.nav.personopplysninger.config;

import no.nav.log.LogFilter;
import no.nav.personopplysninger.features.kodeverk.KodeverkRestConfiguration;
import no.nav.personopplysninger.features.personalia.PersonaliaRestConfiguration;
import no.nav.security.oidc.configuration.MultiIssuerConfiguration;
import no.nav.security.oidc.configuration.OIDCResourceRetriever;
import no.nav.security.oidc.jaxrs.servlet.JaxrsOIDCTokenValidationFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.DispatcherType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EnumSet;

@SpringBootConfiguration
@ComponentScan({"no.nav.personopplysninger.features", "no.nav.personopplysninger.api"})
@EnableConfigurationProperties(MultiIssuerProperties.class)
@Import({RestClientConfiguration.class,
        KodeverkRestConfiguration.class,
        PersonaliaRestConfiguration.class
})
public class ApplicationConfig implements EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(ApplicationConfig.class);

    private Environment env;


    @Bean
    ServletWebServerFactory servletWebServerFactory() {
        JettyServletWebServerFactory serverFactory = new JettyServletWebServerFactory();
        serverFactory.setPort(8080);
        return serverFactory;
    }


    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }


    @Bean
    public ResourceConfig jerseyConfig() {
        return new RestResourceConfiguration();
    }


    @Bean
    public MultiIssuerConfiguration multiIssuerConfiguration(MultiIssuerProperties issuerProperties, OIDCResourceRetriever resourceRetriever) {
        return new MultiIssuerConfiguration(issuerProperties.getIssuer(), resourceRetriever);
    }


    @Bean
    public JaxrsOIDCTokenValidationFilter tokenValidationFilter(MultiIssuerConfiguration config) {
        return new JaxrsOIDCTokenValidationFilter(config);
    }


    @Bean
    public FilterRegistrationBean<JaxrsOIDCTokenValidationFilter> oidcTokenValidationFilterBean(JaxrsOIDCTokenValidationFilter validationFilter) {
        log.info("Registering validation filter");
        final FilterRegistrationBean<JaxrsOIDCTokenValidationFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(validationFilter);
        filterRegistration.setMatchAfter(false);
        filterRegistration
                .setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC));
        filterRegistration.setAsyncSupported(true);
        filterRegistration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegistration;
    }


    @Bean
    public OIDCResourceRetriever oidcResourceRetriever() {
        OIDCResourceRetriever resourceRetriever = new OIDCResourceRetriever();
        resourceRetriever.setProxyUrl(getConfiguredProxy());
        resourceRetriever.setUsePlainTextForHttps(Boolean.parseBoolean(env.getProperty("https.plaintext", "false")));
        return resourceRetriever;
    }


    @Bean
    public FilterRegistrationBean<LogFilter> logFilter() {
        log.info("Registering LogFilter filter");
        final FilterRegistrationBean<LogFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new LogFilter());
        filterRegistration.setOrder(1);
        return filterRegistration;
    }


    private URL getConfiguredProxy() {
        String proxyParameterName = env.getProperty("http.proxy.parametername", "http.proxy");
        String proxyconfig = env.getProperty(proxyParameterName);
        URL proxy = null;
        if (isProxyConfigAvailable(proxyconfig)) {
            log.info("Proxy configuration found [" + proxyParameterName + "] was " + proxyconfig);
            try {
                proxy = new URL(proxyconfig);
            } catch (MalformedURLException e) {
                throw new RuntimeException("config [" + proxyParameterName + "] is misconfigured: " + e, e);
            }
        } else {
            log.info("No proxy configuration found [" + proxyParameterName + "]");
        }
        return proxy;
    }


    private boolean isProxyConfigAvailable(String proxyconfig) {
        return proxyconfig != null && proxyconfig.trim().length() > 0;
    }


    @Override
    public void setEnvironment(Environment env) {
        this.env = env;
    }

}
