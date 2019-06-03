package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.EnhetsKontaktInformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.tps.person.Personinfo

object PersonaliaOgAdresserTransformer {

    fun toOutbound(inbound: Personinfo, kodeverk: PersonaliaKodeverk) = PersonaliaOgAdresser(
            personalia = PersoninfoTransformer.toOutbound(inbound, kodeverk),
            adresser = inbound.adresseinfo?.let { AdresseinfoTransformer.toOutbound(it, kodeverk) },
            enhetKontaktInformasjon = EnhetsKontaktInformasjon()
    )
}