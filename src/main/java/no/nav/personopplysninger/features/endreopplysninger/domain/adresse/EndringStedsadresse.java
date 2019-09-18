package no.nav.personopplysninger.features.endreopplysninger.domain.adresse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import no.nav.personopplysninger.features.endreopplysninger.domain.Endring;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EndringStedsadresse extends Endring<EndringStedsadresse> {
    private Stedsadresse innmeldtEndring;

    public Stedsadresse getInnmeldtEndring() {
        return innmeldtEndring;
    }
}
