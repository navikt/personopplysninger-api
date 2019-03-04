package no.nav.personopplysninger.features.personalia.exceptions;

public class ConsumerException extends RuntimeException {

    public ConsumerException(String msg, Throwable cause) {
        super(msg, cause);
    }


    public ConsumerException(String msg) {
        super(msg);
    }
}
