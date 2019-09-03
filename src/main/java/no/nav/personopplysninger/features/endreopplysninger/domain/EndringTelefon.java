package no.nav.personopplysninger.features.endreopplysninger.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "Responsen fra GET /api/v1/endring/{endringId} når endring gjelder telefonnummer.")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EndringTelefon extends Endring {
    private Telefonnummer innmeldtEndring;

    public Telefonnummer getInnmeldtEndring() {
        return innmeldtEndring;
    }
}
