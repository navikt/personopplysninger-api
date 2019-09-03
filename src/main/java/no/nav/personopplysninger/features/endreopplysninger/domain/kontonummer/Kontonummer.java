package no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Kontonummer {
    private String kilde = "BRUKER SELV";
    private UtenlandskKontoInformasjon utenlandskKontoInformasjon;
    private String value;

    @JsonCreator
    public static Kontonummer create(String json) throws JsonParseException, JsonMappingException, IOException {
        return (new ObjectMapper()).readValue(json, Kontonummer.class);
    }

    public String getKilde() {
        return kilde;
    }

    public UtenlandskKontoInformasjon getUtenlandskKontoInformasjon() {
        return utenlandskKontoInformasjon;
    }

    public String getValue() {
        return value;
    }
}
