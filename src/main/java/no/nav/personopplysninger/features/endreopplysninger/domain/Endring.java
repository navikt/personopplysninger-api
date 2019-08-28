package no.nav.personopplysninger.features.endreopplysninger.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Endring {
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
        return "PENDING".equals(status.getStatusType());
    }



}