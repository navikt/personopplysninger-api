package no.nav.personopplysninger.features.personalia.exceptions;

public class TpsProxyConsumerException extends RuntimeException {

    public TpsProxyConsumerException(String msg, Throwable cause) {
        super(msg, cause);
    }


    public TpsProxyConsumerException(String msg) {
        super(msg);
    }
}
