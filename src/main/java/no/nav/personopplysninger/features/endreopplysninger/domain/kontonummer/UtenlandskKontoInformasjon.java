package no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UtenlandskKontoInformasjon {
    private Bank bank;
    private String landkode;
    private String swift;
    private String valuta;

    public Bank getBank() {
        return bank;
    }

    public String getLandkode() {
        return landkode;
    }

    public String getSwift() {
        return swift;
    }

    public String getValuta() {
        return valuta;
    }
}
