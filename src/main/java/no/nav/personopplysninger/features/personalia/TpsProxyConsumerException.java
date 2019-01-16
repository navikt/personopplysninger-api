package no.nav.personopplysninger.features.personalia;

public class TpsProxyConsumerException extends RuntimeException {
    TpsProxyConsumerException(String msg, Throwable cause) {
        super(msg, cause);
    }


    TpsProxyConsumerException(String msg) {
        super(msg);
    }
}
