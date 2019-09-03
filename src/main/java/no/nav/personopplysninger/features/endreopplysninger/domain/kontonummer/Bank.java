package no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Bank {
    private String adresseLinje1;
    private String adresseLinje2;
    private String adresseLinje3;
    private String kode;
    private String navn;

    public String getAdresseLinje1() {
        return adresseLinje1;
    }

    public String getAdresseLinje2() {
        return adresseLinje2;
    }

    public String getAdresseLinje3() {
        return adresseLinje3;
    }

    public String getKode() {
        return kode;
    }

    public String getNavn() {
        return navn;
    }
}
