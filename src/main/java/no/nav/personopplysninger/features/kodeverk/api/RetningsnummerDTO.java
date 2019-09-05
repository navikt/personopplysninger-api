package no.nav.personopplysninger.features.kodeverk.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RetningsnummerDTO {
    private String retningsnummer;
    private String land;

    public RetningsnummerDTO(String retningsnummer, String land) {
        this.retningsnummer = retningsnummer;
        this.land = land;
    }

    public String getRetningsnummer() {
        return retningsnummer;
    }

    public String getLand() {
        return land;
    }
}
