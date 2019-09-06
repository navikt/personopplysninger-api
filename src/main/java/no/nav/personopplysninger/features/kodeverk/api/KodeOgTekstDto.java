package no.nav.personopplysninger.features.kodeverk.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KodeOgTekstDto {
    private String kode;
    private String tekst;

    public KodeOgTekstDto(String kode, String tekst) {
        this.kode = kode;
        this.tekst= tekst;
    }

    public String getKode() {
        return kode;
    }

    public String getTekst() {
        return tekst;
    }
}
