package no.nav.personopplysninger.features.endreopplysninger.domain.adresse;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Gateadresse {
    @JsonProperty("@type")
    private String subtype = "NORSK_GATEADRESSE";

    private String kilde = "BRUKER SELV";
    private String bolignummer;
    private Integer gatekode;
    private String gatenavn;
    private String gyldigTom;
    private String husbokstav;
    private Integer husnummer;
    private String kommunenummer;
    private String postnummer;
    private String tilleggslinje;
    private String tilleggslinjeType;

    @JsonCreator
    public static Gateadresse create(String json) throws JsonParseException, JsonMappingException, IOException {
        return (new ObjectMapper()).readValue(json, Gateadresse.class);
    }

    public String getKilde() {
        return kilde;
    }

    public String getBolignummer() {
        return bolignummer;
    }

    public Integer getGatekode() {
        return gatekode;
    }

    public String getGatenavn() {
        return gatenavn;
    }

    public String getGyldigTom() {
        return gyldigTom;
    }

    public String getHusbokstav() {
        return husbokstav;
    }

    public Integer getHusnummer() {
        return husnummer;
    }

    public String getKommunenummer() {
        return kommunenummer;
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
}
