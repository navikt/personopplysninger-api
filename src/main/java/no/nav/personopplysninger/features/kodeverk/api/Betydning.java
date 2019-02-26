package no.nav.personopplysninger.features.kodeverk.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@ApiModel(description = "En betydning er en tidsbegrenset periode hvor en gitt kode har en reell betydning. For eksempel kunne koden \"OSLO\" hatt to betydninger: en fra 1048 til 1624, og en fra 1925. Dette er fordi Oslo ble omdøpt til Christiania i en periode.")
@JsonIgnoreProperties
public class Betydning {

    @ApiModelProperty(value = "Når denne betydningen trådte i kraft, på YYYY-MM-DD format.", required = true)
    private LocalDate gyldigFra;

    @ApiModelProperty(value = "Når denne betydningen slutter å være gyldig, på YYYY-MM-DD format.", required = true)
    private LocalDate gyldigTil;

    @ApiModelProperty(value = "En samling beskrivelser for denne betydningen, mappet til en språkkode.", required = true)
    private Map<String, Beskrivelse> beskrivelser;

    public Map<String, Beskrivelse> getBeskrivelser() {
        if (beskrivelser == null) {
            beskrivelser = new HashMap<>();
        }
        return beskrivelser;
    }
}