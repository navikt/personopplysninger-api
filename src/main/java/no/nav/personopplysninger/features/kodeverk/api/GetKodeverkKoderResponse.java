package no.nav.personopplysninger.features.kodeverk.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "Responsen fra GET /api/v1/kodeverk/{kodeverksnavn}/koder.")
@JsonIgnoreProperties
public class GetKodeverkKoderResponse {

    @ApiModelProperty(value = "En liste med alle de eksisterende kodene som tilhører kodeverket.")
    private final List<String> koder = new ArrayList<>();

    public GetKodeverkKoderResponse() {
    }

    public GetKodeverkKoderResponse(List<String> koder) {
        this.koder.addAll(koder);
    }
}
