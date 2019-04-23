package no.nav.personopplysninger.features.arbeidsforhold.dto.transformer

import no.nav.personopplysninger.features.arbeidsforhold.domain.PermisjonPermittering
import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.PermisjonPermitteringDto
import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.UtenlandsoppholdDto

object PermisjonPermitteringTransformer {

    fun toOutbound(inbound: PermisjonPermittering) = PermisjonPermitteringDto(
            periode = PeriodeTransformer.toOutboundfromPeriode(inbound.periode),
            type = inbound.type,
            prosent = inbound.prosent.toString()
    )

    fun toOutboundArray(inbound: Array<PermisjonPermittering>?): ArrayList<PermisjonPermitteringDto> {
        var permisjonPermitteringDtoArray = ArrayList<PermisjonPermitteringDto>()

        for (permisjon in inbound.orEmpty()) {
            var pdto = PermisjonPermitteringDto(
                    type = permisjon.type,
                    prosent = permisjon.prosent.toString(),
                    periode = PeriodeTransformer.toOutboundfromPeriode(permisjon.periode)
            )
            permisjonPermitteringDtoArray.add(pdto)
        }

        return permisjonPermitteringDtoArray
    }

}
