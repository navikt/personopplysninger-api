package no.nav.personopplysninger.features.endreopplysninger.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Status {
    private Integer endringId;
    private String statusType;
    private List<Substatus> substatus;

    public Integer getEndringId() {
        return endringId;
    }

    public String getStatusType() {
        return statusType;
    }

    public List<Substatus> getSubstatus() {
        return substatus;
    }
}
