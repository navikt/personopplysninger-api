package no.nav.personopplysninger.features.endreopplysninger.domain.adresse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import no.nav.personopplysninger.features.endreopplysninger.domain.Endring;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EndringOpphoerAdresse extends Endring<EndringOpphoerAdresse> {
    private String innmeldtEndring;

    public String getInnmeldtEndring() {
        return innmeldtEndring;
    }
}
