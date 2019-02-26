package no.nav.personopplysninger.features.kodeverk.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@ApiModel(description = "Responsen fra GET /api/v1/kodeverk/{kodeverksnavn}/koder/betydninger.")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetKodeverkKoderBetydningerResponse {

    @ApiModelProperty(value = "Et map med alle eksisterende koder for kodeverket og alle tilhørende betydninger som passer søkekriteriene.")
    private Map<String, List<Betydning>> betydninger;

    public GetKodeverkKoderBetydningerResponse(Map<String, List<Betydning>> betydninger) {
        setBetydninger(betydninger);
    }

    public GetKodeverkKoderBetydningerResponse() {
    }

    public void setBetydninger(Map<String, List<Betydning>> betydninger) {
        this.betydninger = new LinkedHashMap<>(betydninger);
    }

}