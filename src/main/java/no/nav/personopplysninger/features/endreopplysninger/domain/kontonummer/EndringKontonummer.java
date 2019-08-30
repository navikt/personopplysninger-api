package no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import no.nav.personopplysninger.features.endreopplysninger.domain.Endring;

@ApiModel(description = "Responsen fra GET /api/v1/endring/{endringId} når endring gjelder kontonummer.")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EndringKontonummer  extends Endring {
    private Kontonummer innmeldtEndring;

    public Kontonummer getInnmeldtEndring() {
        return innmeldtEndring;
    }
}
