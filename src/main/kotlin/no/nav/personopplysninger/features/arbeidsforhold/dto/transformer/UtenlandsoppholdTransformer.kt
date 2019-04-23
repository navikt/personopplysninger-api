package no.nav.personopplysninger.features.arbeidsforhold.dto.transformer

import no.nav.personopplysninger.features.arbeidsforhold.domain.Arbeidsforhold
import no.nav.personopplysninger.features.arbeidsforhold.domain.Utenlandsopphold
import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.ArbeidsforholdDto
import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.UtenlandsoppholdDto

object UtenlandsoppholdTransformer {

    fun toOutbound(inbound: Utenlandsopphold) = UtenlandsoppholdDto(
            periode = PeriodeTransformer.toOutboundfromPeriode(inbound.periode),
            land = inbound.landkode
    )


    fun toOutboundArray(inbound: Array<Utenlandsopphold>?): ArrayList<UtenlandsoppholdDto> {
        var utenlandsoppholdDtoArray = ArrayList<UtenlandsoppholdDto>()

        for (opphold in inbound.orEmpty()) {
            var udto = UtenlandsoppholdDto(
                    land = opphold.landkode,
                    periode = PeriodeTransformer.toOutboundfromPeriode(opphold.periode)
            )
            utenlandsoppholdDtoArray.add(udto)
        }

        return utenlandsoppholdDtoArray
    }


}
