package no.nav.personopplysninger.personalia.transformer

import kotlinx.serialization.json.JsonObject
import no.nav.personopplysninger.common.consumer.kontoregister.dto.outbound.Konto
import no.nav.personopplysninger.common.consumer.pdl.dto.PdlData
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.PersonaliaOgAdresser

object PersonaliaOgAdresserTransformer {

    fun toOutbound(
        pdlData: PdlData,
        konto: Konto?,
        kodeverk: PersonaliaKodeverk,
        enhetKontaktInformasjon: JsonObject?
    ) =
        PersonaliaOgAdresser(
            personalia = PersoninfoTransformer.toOutbound(pdlData.person, konto, kodeverk),
            adresser = AdresseinfoTransformer.toOutbound(pdlData, kodeverk),
            enhetKontaktInformasjon = enhetKontaktInformasjon
        )
}