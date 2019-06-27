package no.nav.personopplysninger.features.norg2.exceptions;


public class Norg2ConsumerException extends RuntimeException {

    public Norg2ConsumerException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public Norg2ConsumerException(String msg) {
        super(msg);
    }
}
