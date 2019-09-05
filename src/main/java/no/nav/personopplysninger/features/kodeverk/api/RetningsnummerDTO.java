package no.nav.personopplysninger.features.kodeverk.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RetningsnummerDTO {
    private String landskode;
    private String land;

    public RetningsnummerDTO(String landskode, String land) {
        RetningsnummerDTO[] dtos = new RetningsnummerDTO[10];
        this.landskode = landskode;
        this.land = land;
    }

    public String getLandskode() {
        return landskode;
    }

    public String getLand() {
        return land;
    }
}
