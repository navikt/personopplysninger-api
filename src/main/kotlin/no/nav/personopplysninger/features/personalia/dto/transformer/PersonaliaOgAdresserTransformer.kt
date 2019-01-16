package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.tps.person.Personinfo

object PersonaliaOgAdresserTransformer {

    fun toOutbound(inbound: Personinfo) = PersonaliaOgAdresser(
            personalia = PersoninfoTransformer.toOutbound(inbound),
            adresser = inbound.adresseinfo?.let { AdresseinfoTransformer.toOutbound(it) }
    )
}
