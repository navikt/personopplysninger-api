package no.nav.personopplysninger.features.endreopplysninger.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Telefonnummer {
    private String kilde;
    private String landskode = null;
    private Integer nummer;
    private String type;

    public Telefonnummer() {}

    public Telefonnummer(String kilde, String landskode, Integer nummer, String type) {
        this.kilde = kilde;
        this.landskode = landskode;
        this.nummer = nummer;
        this.type = type;
    }

    public String getKilde() {
        return kilde;
    }

    public String getLandskode() {
        return landskode;
    }

    public Integer getNummer() {
        return nummer;
    }

    public String getType() {
        return type;
    }
}
