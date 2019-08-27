package no.nav.personopplysninger.features.endreopplysninger.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;


@ApiModel(description = "Responsen fra GET /api/v1/endring/{endringId}.")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Endring {

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

    public boolean isPending() {
        return "PENDING".equals(status.statusType);
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