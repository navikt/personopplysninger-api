package no.nav.personopplysninger.config;

import no.nav.common.log.LogFilter;
import no.nav.common.utils.EnvironmentUtils;
import no.nav.security.token.support.core.configuration.MultiIssuerConfiguration;
import no.nav.security.token.support.core.configuration.ProxyAwareResourceRetriever;
import no.nav.security.token.support.jaxrs.servlet.JaxrsJwtTokenValidationFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.RequestContextFilter;

import javax.servlet.DispatcherType;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.EnumSet;

@SpringBootConfiguration
@ComponentScan({
        "no.nav.personopplysninger.features",
        "no.nav.personopplysninger.api",
        "no.nav.personopplysninger.oppslag",
        "no.nav.personopplysninger.tasks"
})
@EnableConfigurationProperties(MultiIssuerProperties.class)
@Cacheable
@Import({RestClientConfiguration.class})
public class ApplicationConfig implements EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(ApplicationConfig.class);

    private Environment env;

    @Bean
    public RequestContextFilter requestContextFilter() {
        OrderedRequestContextFilter filter = new OrderedRequestContextFilter();
        filter.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return filter;
    }

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
    public MultiIssuerConfiguration multiIssuerConfiguration(MultiIssuerProperties issuerProperties, ProxyAwareResourceRetriever resourceRetriever) {
        return new MultiIssuerConfiguration(issuerProperties.getIssuer(), resourceRetriever);
    }

    @Bean
    public JaxrsJwtTokenValidationFilter tokenValidationFilter(MultiIssuerConfiguration config) {
        return new JaxrsJwtTokenValidationFilter(config);
    }

    @Bean
    public FilterRegistrationBean<JaxrsJwtTokenValidationFilter> oidcTokenValidationFilterBean(JaxrsJwtTokenValidationFilter validationFilter) {
        log.info("Registering validation filter");
        final FilterRegistrationBean<JaxrsJwtTokenValidationFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(validationFilter);
        filterRegistration.setMatchAfter(false);
        filterRegistration
                .setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC));
        filterRegistration.setAsyncSupported(true);
        filterRegistration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegistration;
    }

    @Bean
    public ProxyAwareResourceRetriever oidcResourceRetriever() {
        URL proxyUrl = getConfiguredProxy();
        ProxyAwareResourceRetriever resourceRetriever = new ProxyAwareResourceRetriever();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyUrl.getHost(), proxyUrl.getPort()));
        resourceRetriever.setProxy(proxy);
        return resourceRetriever;
    }

    @Bean
    public FilterRegistrationBean<LogFilter> logFilter() {
        log.info("Registering LogFilter filter");
        final FilterRegistrationBean<LogFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new LogFilter(EnvironmentUtils.requireApplicationName()));

        // Viktig at order settes til en lavere verdi enn hva jersey-filteret er konfigurert med i application-yaml
        // slik at loggfilteret kjøres først. Årsaken til dette er avhengigheter til MDC i forretningskoden (uthenting av callId).
        filterRegistration.setOrder(-100001);
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
