package no.nav.personopplysninger.features.endreopplysninger.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationError {
    private String message;
    private List<ValidationDetail> details;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public List<ValidationDetail> getDetails() {
        return details;
    }
}
