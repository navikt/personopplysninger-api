package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.common.pdl.dto.PdlData
import no.nav.personopplysninger.personalia.consumer.tpsproxy.dto.Personinfo
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.EnhetsKontaktInformasjon
import no.nav.personopplysninger.personalia.dto.outbound.PersonaliaOgAdresser

object PersonaliaOgAdresserTransformer {

    fun toOutbound(inbound: Personinfo, pdlData: PdlData, kodeverk: PersonaliaKodeverk) =
        PersonaliaOgAdresser(
            personalia = PersoninfoTransformer.toOutbound(inbound, pdlData.person, kodeverk),
            adresser = AdresseinfoTransformer.toOutbound(pdlData, kodeverk),
            enhetKontaktInformasjon = EnhetsKontaktInformasjon()
        )
}