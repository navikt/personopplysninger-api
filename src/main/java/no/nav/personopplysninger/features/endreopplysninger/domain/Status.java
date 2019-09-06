package no.nav.personopplysninger.features.endreopplysninger.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Status {
    private Integer endringId;
    private String statusType;
    private Substatus substatus;

    public Integer getEndringId() {
        return endringId;
    }

    public String getStatusType() {
        return statusType;
    }

    public Substatus getSubstatus() {
        return substatus;
    }
}
