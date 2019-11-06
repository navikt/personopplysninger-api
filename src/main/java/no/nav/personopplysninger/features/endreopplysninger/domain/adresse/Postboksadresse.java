package no.nav.personopplysninger.features.endreopplysninger.domain.adresse;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Postboksadresse {
    @JsonProperty("@type")
    private String subtype = "NORSK_POSTBOKSADRESSE";

    private String kilde = "BRUKER SELV";
    private String gyldigTom;
    private String kommunenummer;
    private String postboksnummer;
    private String postnummer;
    private String tilleggslinje;
    private String tilleggslinjeType;
    private String postboksanlegg;

    @JsonCreator
    public static Postboksadresse create(String json) throws JsonParseException, JsonMappingException, IOException {
        return (new ObjectMapper()).readValue(json, Postboksadresse.class);
    }

    public String getKilde() {
        return kilde;
    }

    public String getGyldigTom() {
        return gyldigTom;
    }

    public String getKommunenummer() {
        return kommunenummer;
    }

    public String getPostboksnummer() {
        return postboksnummer;
    }

    public String getPostnummer() {
        return postnummer;
    }

    public String getTilleggslinje() {
        return tilleggslinje;
    }

    public String getTilleggslinjeType() {
        return tilleggslinjeType;
    }

    public String getPostboksanlegg() {
        return postboksanlegg;
    }
}
