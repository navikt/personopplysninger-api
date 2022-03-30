package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.consumer.kodeverk.domain.PersonaliaKodeverk
import no.nav.personopplysninger.consumer.pdl.dto.PdlData
import no.nav.personopplysninger.features.personalia.dto.outbound.EnhetsKontaktInformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.tps.person.Personinfo

object PersonaliaOgAdresserTransformer {

    fun toOutbound(inbound: Personinfo, pdlData: PdlData, kodeverk: PersonaliaKodeverk) =
        PersonaliaOgAdresser(
            personalia = PersoninfoTransformer.toOutbound(inbound, pdlData.person!!, kodeverk),
            adresser = AdresseinfoTransformer.toOutbound(pdlData, kodeverk),
            enhetKontaktInformasjon = EnhetsKontaktInformasjon()
        )
}