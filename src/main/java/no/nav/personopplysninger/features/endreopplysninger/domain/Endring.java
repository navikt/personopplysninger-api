package no.nav.personopplysninger.features.endreopplysninger.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Endring<T> {
    private String endringstype;
    private String ident;
    private String lineage;
    private String opplysningsId;
    private Status status;
    private String statusType = "OK";
    private Error error;

    public String getEndringstype() {
        return endringstype;
    }

    public String getIdent() {
        return ident;
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

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public void createValidationErrorIfTpsHasError() {
        if (hasTpsError()) {
            Error validationError = new Error();
            validationError.setMessage(getTpsBeskrivelse());
            setStatusType("ERROR");
            setError(validationError);
        }
    }

    @JsonIgnore
    public boolean isPending() {
        return "PENDING".equals(status.getStatusType());
    }

    @JsonIgnore
    public boolean isDoneWithoutTpsError() {
        if (!"DONE".equals(status.getStatusType())) {
            return false;
        }
        return !hasTpsError();
    }

    @JsonIgnore
    private boolean hasTpsError() {
        for (Substatus substatus: status.getSubstatus()) {
            if ("TPS".equalsIgnoreCase(substatus.getDomene())) {
                return "ERROR".equals(substatus.getStatus());
            }
        }
        return false;
    }

    @JsonIgnore
    private String getTpsBeskrivelse() {
        for (Substatus substatus: status.getSubstatus()) {
            if ("TPS".equalsIgnoreCase(substatus.getDomene())) {
                return substatus.getBeskrivelse();
            }
        }
        return null;
    }
}