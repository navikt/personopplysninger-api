package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.EnhetsKontaktInformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlPersonInfo
import no.nav.tps.person.Personinfo

object PersonaliaOgAdresserTransformer {

    fun toOutbound(inbound: Personinfo, pdlPersonInfo: PdlPersonInfo, kodeverk: PersonaliaKodeverk) = PersonaliaOgAdresser(
            personalia = PersoninfoTransformer.toOutbound(inbound, pdlPersonInfo, kodeverk),
            adresser = AdresseinfoTransformer.toOutbound(inbound.adresseinfo, pdlPersonInfo.kontaktadresse, kodeverk),
            enhetKontaktInformasjon = EnhetsKontaktInformasjon()
    )
}