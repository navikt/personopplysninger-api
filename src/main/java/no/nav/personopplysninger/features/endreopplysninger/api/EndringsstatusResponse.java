package no.nav.personopplysninger.features.endreopplysninger.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "Responsen fra GET /api/v1/endring/{endringId}.")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EndringsstatusResponse {

    @ApiModelProperty(value = "TODO")
    private String endringstype;

    private String ident;

    private Status status;

    public Status getStatus() {
        return status;
    }

    public String getIdent() {
        return ident;
    }

    public String getEndringstype() {
        return endringstype;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Status {
        private Integer endringId;
        private String statusType;

        public Integer getEndringId() {
            return endringId;
        }

        public String getStatusType() {
            return statusType;
        }
    }

}