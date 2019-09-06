package no.nav.personopplysninger.features.endreopplysninger.domain.adresse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import no.nav.personopplysninger.features.endreopplysninger.domain.Endring;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EndringGateadresse extends Endring<EndringGateadresse> {
    private Gateadresse innmeldtEndring;

    public Gateadresse getInnmeldtEndring() {
        return innmeldtEndring;
    }
}
