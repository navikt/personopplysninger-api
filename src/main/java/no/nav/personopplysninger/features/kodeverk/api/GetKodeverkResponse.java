package  no.nav.personopplysninger.features.kodeverk.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.List;

@ApiModel(description = "Responsen fra GET /api/v1/kodeverk.")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetKodeverkResponse {

    @ApiModelProperty(value = "En liste med navnene på alle eksisterende kodeverk.")
    private List<String> kodeverksnavn;

    public GetKodeverkResponse() {
    }

    public GetKodeverkResponse(List<String> kodeverksnavn) {
        this.kodeverksnavn = kodeverksnavn;
    }
}
