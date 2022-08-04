package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.consumer.kodeverk.dto.PersonaliaKodeverk
import no.nav.personopplysninger.consumer.kontoregister.dto.Konto
import no.nav.personopplysninger.consumer.pdl.dto.PdlData
import no.nav.personopplysninger.features.personalia.dto.outbound.EnhetsKontaktInformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.PersonaliaOgAdresser

object PersonaliaOgAdresserTransformer {

    fun toOutbound(pdlData: PdlData, konto: Konto?, kodeverk: PersonaliaKodeverk) =
        PersonaliaOgAdresser(
            personalia = PersoninfoTransformer.toOutbound(pdlData.person!!, konto, kodeverk),
            adresser = AdresseinfoTransformer.toOutbound(pdlData, kodeverk),
            enhetKontaktInformasjon = EnhetsKontaktInformasjon()
        )
}