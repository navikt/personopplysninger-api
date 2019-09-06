package no.nav.personopplysninger.features.endreopplysninger.domain.adresse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import no.nav.personopplysninger.features.endreopplysninger.domain.Endring;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EndringPostboksadresse extends Endring<EndringPostboksadresse> {
    private Postboksadresse innmeldtEndring;

    public Postboksadresse getInnmeldtEndring() {
        return innmeldtEndring;
    }
}
