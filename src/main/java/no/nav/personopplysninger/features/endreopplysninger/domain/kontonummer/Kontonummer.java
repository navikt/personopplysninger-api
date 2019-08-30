package no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Kontonummer {
    private String kilde = "BRUKER SELV";
    private UtenlandskKontoInformasjon utenlandskKontoInformasjon;
    private String value;

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
