package  no.nav.personopplysninger.features.kodeverk.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.List;

@ApiModel(description = "Responsen fra GET /api/v1/kodeverk.")
public class GetKodeverkResponse {

    @ApiModelProperty(value = "En liste med navnene på alle eksisterende kodeverk.", required = true)
    private List<String> kodeverksnavn;

    public GetKodeverkResponse(List<String> kodeverksnavn) {
        this.kodeverksnavn = kodeverksnavn;
    }
}
