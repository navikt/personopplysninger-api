package no.nav.personopplysninger.features.kodeverk;

import no.nav.log.MDCConstants;
import no.nav.personopplysninger.features.kodeverk.exceptions.KodeverkConsumerException;
import no.nav.personopplysninger.features.personalia.kodeverk.Kjoennstype;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import no.nav.tjeneste.virksomhet.kodeverk.v2.KodeverkPortType;
import no.nav.sbl.dialogarena.common.cxf.CXFClient;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static no.nav.sbl.dialogarena.common.cxf.InstanceSwitcher.createMetricsProxyWithInstanceSwitcher;

public class KodeverkConsumer {

    private static final String CONSUMER_ID = "personbruker-personopplysninger-api";
    public static final String KODEVERK_MOCK_KEY = "kodeverk.personopplysninger.withmock";
    private Client client;
    private URI endpoint;

    public KodeverkConsumer(Client client, URI endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }

    @Bean
    public KodeverkPortType kodeverkPortType() {
        final KodeverkPortType prod = createKodeverkPortTypeCXFClient().configureStsForSubject().build();
        return createMetricsProxyWithInstanceSwitcher("kodeverk", prod, prod, KODEVERK_MOCK_KEY, KodeverkPortType.class);
    }

    public KodeverkPortType selfTestKodeverkPortType() {
        return createKodeverkPortTypeCXFClient().configureStsForSystemUser().build();
    }


    private CXFClient<KodeverkPortType> createKodeverkPortTypeCXFClient() {
        return new CXFClient<>(KodeverkPortType.class)
                .timeout(15000, 15000)
                .address(endpoint.toString())
                .wsdl("classpath:kodeverk/no/nav/tjeneste/virksomhet/kodeverk/v2/Kodeverk.wsdl")
                .withProperty("ws-security.must-understand", false); /* setter mustunderstand i header slik at tjenester som ikke forstår sikkerhetsheader ikke skal avvise requester
                                                                       Burde brukt konstant fra org.apache.cxf.ws.security.SecurityConstants.MUST_UNDERSTAND, men det går ikke pga.
                                                                       dependency fucks siden det er det eneste vi bruker fra biblioteket */
    }

    public Kjoennstype hentKjonn(String kode) {
        Invocation.Builder request = buildRequest(kode);
        return hentKjonn(request);
    }


    private Invocation.Builder buildRequest(String kode) {
        return client.target(endpoint)
                .path("kodeverk")
                .request()
                .header("Nav-Call-Id", MDC.get(MDCConstants.MDC_CALL_ID))
                .header("Nav-Consumer-Id", CONSUMER_ID)
                .header("Nav-Personident", kode);
    }


    private Kjoennstype hentKjonn(Invocation.Builder request) {
        try (Response response = request.get()) {
            return readResponse(response);
        } catch (KodeverkConsumerException e) {
            throw e;
        } catch (Exception e) {
            String msg = "Forsøkte å konsumere kodeverk. endpoint=[" + endpoint + "]." + e.getStackTrace().toString() + e.getMessage();
            throw new KodeverkConsumerException(msg, e);
        }
    }


    private Kjoennstype readResponse(Response r) {
        if (!SUCCESSFUL.equals(r.getStatusInfo().getFamily())) {
            String msg = "Forsøkte å konsumere kodeverk. endpoint=[" + endpoint + "], HTTP response status=[" + r.getStatus() + "].";
            throw new KodeverkConsumerException(msg + " - " + readEntity(String.class, r));
        } else {
            return readEntity(Kjoennstype.class, r);
        }
    }


    private <T> T readEntity(Class<T> responsklasse, Response response) {
        try {
            return response.readEntity(responsklasse);
        } catch (ProcessingException e) {
            throw new KodeverkConsumerException("Prosesseringsfeil på responsobjekt. Responsklasse: " + responsklasse.getName(), e);
        } catch (IllegalStateException e) {
            throw new KodeverkConsumerException("Ulovlig tilstand på responsobjekt. Responsklasse: " + responsklasse.getName(), e);
        } catch (Exception e) {
            throw new KodeverkConsumerException("Uventet feil på responsobjektet. Responsklasse: " + responsklasse.getName(), e);
        }
    }
}
