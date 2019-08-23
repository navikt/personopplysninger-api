package no.nav.personopplysninger.features.endreopplysninger.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "Responsen fra POST /api/v1/endring/telefonnummer.")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OppdaterTelefonnumerResponse {

    @ApiModelProperty(value = "TODO")
    private String statusCode;

    @ApiModelProperty(value = "TODO")
    private Integer statusCodeValue;

    public OppdaterTelefonnumerResponse() {
    }

    public String getStatusCode() {
        return statusCode;
    }

    public Integer getStatusCodeValue() {
        return statusCodeValue;
    }
}