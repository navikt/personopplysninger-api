package no.nav.personopplysninger.features.endreopplysninger.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.lang.reflect.ParameterizedType;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Endring<T> {
    private String endringstype;
    private String ident;
    private String lineage;
    private String opplysningsId;
    private Status status;

    private Class<T> genericClass;

    public Endring() {
        this.genericClass = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

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
    public Class<T> getGenericClass() {
        return genericClass;
    }

    @JsonIgnore
    public boolean isPending() {
        return "PENDING".equals(status.getStatusType()) || status.getSubStatus() == null || "PENDING".equals(status.getSubStatus().getStatus());
    }

    @JsonIgnore
    public boolean isDoneDone() {
        return "DONE".equals(status.getStatusType()) && status.getSubStatus() != null  && "DONE".equals(status.getSubStatus().getStatus());
    }

}