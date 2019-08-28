package no.nav.personopplysninger.features.endreopplysninger.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;


@ApiModel(description = "Responsen fra GET /api/v1/endring/{endringId}.")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Endring {
    private String endringstype;
    private String ident;
    private String innmeldtEndring;
    private String lineage;
    private String opplysningsId;
    private Status status;

    public String getEndringstype() {
        return endringstype;
    }

    public String getIdent() {
        return ident;
    }

    public String getInnmeldtEndring() {
        return innmeldtEndring;
    }

    public String getLineage() {
        return lineage;
    }

    public String getOpplysningsId() {
        return opplysningsId;
    }

    public Status getStatus() {
        return status;
    }

    @JsonIgnore
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