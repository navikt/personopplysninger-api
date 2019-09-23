package no.nav.personopplysninger.features.endreopplysninger.domain.adresse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import no.nav.personopplysninger.features.endreopplysninger.domain.Endring;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EndringUtenlandsadresse extends Endring<EndringUtenlandsadresse> {
    private Utenlandsadresse innmeldtEndring;

    public Utenlandsadresse getInnmeldtEndring() {
        return innmeldtEndring;
    }
}