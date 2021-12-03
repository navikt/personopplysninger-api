package no.nav.personopplysninger.features.personaliagammel.dto.transformer

import no.nav.personopplysninger.features.personaliagammel.dto.outbound.EnhetsKontaktInformasjon
import no.nav.personopplysninger.features.personaliagammel.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.features.personaliagammel.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personaliagammel.pdl.dto.PdlPersonInfo
import no.nav.tps.person.Personinfo

object PersonaliaOgAdresserTransformer {

    fun toOutbound(inbound: Personinfo, pdlPersonInfo: PdlPersonInfo, kodeverk: PersonaliaKodeverk) = PersonaliaOgAdresser(
            personalia = PersoninfoTransformer.toOutbound(inbound, pdlPersonInfo, kodeverk),
            adresser = AdresseinfoTransformer.toOutbound(inbound.adresseinfo, pdlPersonInfo.kontaktadresse, kodeverk),
            enhetKontaktInformasjon = EnhetsKontaktInformasjon()
    )
}