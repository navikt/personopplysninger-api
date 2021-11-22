package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.EnhetsKontaktInformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser
import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresserMigrert
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlData
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlPerson
import no.nav.tps.person.Personinfo

object PersonaliaOgAdresserTransformer {

    fun toOutbound(inbound: Personinfo, pdlPerson: PdlPerson, kodeverk: PersonaliaKodeverk) = PersonaliaOgAdresser(
        personalia = PersoninfoTransformer.toOutbound(inbound, pdlPerson, kodeverk),
        adresser = AdresseinfoTransformer.toOutbound(inbound.adresseinfo, pdlPerson.kontaktadresse, kodeverk),
        enhetKontaktInformasjon = EnhetsKontaktInformasjon()
    )

    fun toOutboundMigrert(inbound: Personinfo, pdlData: PdlData, kodeverk: PersonaliaKodeverk) =
        PersonaliaOgAdresserMigrert(
            personalia = PersoninfoTransformer.toOutboundMigrert(inbound, pdlData.person!!, kodeverk),
            adresser = AdresseinfoTransformer.toOutboundMigrert(pdlData, kodeverk),
            enhetKontaktInformasjon = EnhetsKontaktInformasjon()
        )
}