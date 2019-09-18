package no.nav.personopplysninger.features.endreopplysninger.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
