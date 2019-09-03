package no.nav.personopplysninger.features.endreopplysninger.domain.telefon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import no.nav.personopplysninger.features.endreopplysninger.domain.Endring;
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.Telefonnummer;

@ApiModel(description = "Responsen fra GET /api/v1/endring/{endringId} når endring gjelder telefonnummer.")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EndringTelefon extends Endring<EndringTelefon> {
    private Telefonnummer innmeldtEndring;

    public Telefonnummer getInnmeldtEndring() {
        return innmeldtEndring;
    }
}
