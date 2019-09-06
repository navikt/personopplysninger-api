package no.nav.personopplysninger.features.endreopplysninger.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Substatus {
    private String beskrivelse;
    private String domene;
    private String kode;
    private String referanse;
    private String status;

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public String getDomene() {
        return domene;
    }

    public String getKode() {
        return kode;
    }

    public String getReferanse() {
        return referanse;
    }

    public String getStatus() {
        return status;
    }
}
