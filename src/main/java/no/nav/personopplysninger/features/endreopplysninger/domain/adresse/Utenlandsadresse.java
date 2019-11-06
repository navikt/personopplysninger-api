package no.nav.personopplysninger.features.endreopplysninger.domain.adresse;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Utenlandsadresse {
    @JsonProperty("@type")
    private String subtype = "UTENLANDSK_KONTAKTADRESSE";

    private String kilde = "BRUKER SELV";
    private String adresselinje1;
    private String adresselinje2;
    private String adresselinje3;
    private String gyldigTom;
    private String landkode;

    @JsonCreator
    public static Utenlandsadresse create(String json) throws JsonParseException, JsonMappingException, IOException {
        return (new ObjectMapper()).readValue(json, Utenlandsadresse.class);
    }

    public String getKilde() {
        return kilde;
    }

    public String getAdresselinje1() {
        return adresselinje1;
    }

    public String getAdresselinje2() {
        return adresselinje2;
    }

    public String getAdresselinje3() {
        return adresselinje3;
    }

    public String getGyldigTom() {
        return gyldigTom;
    }

    public String getLandkode() {
        return landkode;
    }
}
