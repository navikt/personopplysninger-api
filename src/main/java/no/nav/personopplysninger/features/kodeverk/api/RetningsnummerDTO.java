package no.nav.personopplysninger.features.kodeverk.api;

public class RetningsnummerDTO {
    private String retningsnummer;
    private String land;



    public RetningsnummerDTO(String retningsnummer, String land) {
        RetningsnummerDTO[] dtos = new RetningsnummerDTO[10];
        this.retningsnummer = retningsnummer;
        this.land = land;
    }
}
