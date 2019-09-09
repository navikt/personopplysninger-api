package no.nav.personopplysninger.features.endreopplysninger.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Endring<T> {
    private String endringstype;
    private String ident;
    private String lineage;
    private String opplysningsId;
    private Status status;

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

    @JsonIgnore
    public boolean isPending() {
        if ("PENDING".equals(status.getStatusType())) {
            return true;
        }
        boolean subtypePending = true;
        for (Substatus substatus: status.getSubstatus()) {
            if (!"PENDING".equals(substatus.getStatus())) {
                subtypePending = false;
            }
        }
        return subtypePending;
    }

    @JsonIgnore
    public boolean isDoneDone() {
        if (!"DONE".equals(status.getStatusType())) {
            return false;
        }
        boolean subtypeDone = true;
        for (Substatus substatus: status.getSubstatus()) {
            if (!"DONE".equals(substatus.getStatus())) {
                subtypeDone = false;
            }
        }
        return subtypeDone;
    }

}