package no.nav.personopplysninger.features.endreopplysninger.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationDetail {
    private String name;
    private String message;

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
