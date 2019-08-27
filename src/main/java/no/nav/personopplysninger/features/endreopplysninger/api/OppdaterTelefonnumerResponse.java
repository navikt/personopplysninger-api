package no.nav.personopplysninger.features.endreopplysninger.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "Responsen fra GET /api/v1/endring/{endringId}.")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OppdaterTelefonnumerResponse {

    @ApiModelProperty(value = "TODO")
    private String endringstype;


    public OppdaterTelefonnumerResponse() {
    }

    public String getEndringstype() {
        return endringstype;
    }

}