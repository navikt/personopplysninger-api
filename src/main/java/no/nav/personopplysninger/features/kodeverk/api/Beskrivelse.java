package no.nav.personopplysninger.features.kodeverk.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "En beskrivelse er den tekstlige delen av betydningen til en kode, og den kan derfor komme på flere språk. For eksempel, landkoden \"NOR\" kan ha beskrivelsen \"Norge\" på norsk, men \"Norway\" på engelsk. Dersom man ber om å få beskrivelsene på et språk som ikke finnes, så vil bokmålsversjonen brukes isteden.")
public class Beskrivelse {

    @ApiModelProperty(value = "En kort versjon av beskrivelsen, og passer derfor godt til fremvisning i GUI-elementer.", required = true)
    private String term;

    @ApiModelProperty(value = "En mer utfyllende versjon av beskrivelsen, og derfor passer denne verdien bedre som ledetekster der antall tegn ikke er et like stort problem. Ikke alle beskrivelser har en utfyllende versjon, og i de tilfellene vil kortversjonen gå igjen i dette feltet.", required = true)
    private String tekst;

    public String getTerm() {
        return term;
    }

    public String getTekst() {
        return tekst;
    }

}