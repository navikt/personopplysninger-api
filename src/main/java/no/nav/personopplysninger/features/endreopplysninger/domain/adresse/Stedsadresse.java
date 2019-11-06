package no.nav.personopplysninger.features.endreopplysninger.domain.adresse;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stedsadresse {
    @JsonProperty("@type")
    private String subtype = "NORSK_STEDSADRESSE";

    private String kilde = "BRUKER SELV";
    private String eiendomsnavn;
    private String gyldigTom;
    private String kommunenummer;
    private String postnummer;
    private String tilleggslinje;
    private String tilleggslinjeType;

    @JsonCreator
    public static Stedsadresse create(String json) throws JsonParseException, JsonMappingException, IOException {
        return (new ObjectMapper()).readValue(json, Stedsadresse.class);
    }

    public String getKilde() {
        return kilde;
    }

    public String getEiendomsnavn() {
        return eiendomsnavn;
    }

    public String getGyldigTom() {
        return gyldigTom;
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
