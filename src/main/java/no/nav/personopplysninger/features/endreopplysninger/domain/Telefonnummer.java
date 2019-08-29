package no.nav.personopplysninger.features.endreopplysninger.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Telefonnummer {
    private String kilde = "BRUKER SELV";
    private String landskode = null;
    private Integer nummer;
    private String type;

    public Telefonnummer() {}

    // Denne var nødvendig fordi verdien på feltet 'innmeldtEndring' (som kan representere et Telefonnummer) er
    // en json representert som en string og wrappet med dobbeltfnutter.
    @JsonCreator
    public static Telefonnummer create(String json) throws JsonParseException, JsonMappingException, IOException {
        return (new ObjectMapper()).readValue(json, Telefonnummer.class);
    }

    public Telefonnummer(String kilde, String landskode, Integer nummer, String type) {
        this.kilde = kilde;
        this.landskode = landskode;
        this.nummer = nummer;
        this.type = type;
    }

    public Telefonnummer(String landskode, Integer nummer, String type) {
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
