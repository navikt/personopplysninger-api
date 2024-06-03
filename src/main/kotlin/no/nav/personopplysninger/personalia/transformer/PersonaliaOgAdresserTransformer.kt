package no.nav.personopplysninger.personalia.transformer

import no.nav.pdl.generated.dto.HentPersonQuery
import no.nav.personopplysninger.consumer.kontoregister.dto.outbound.Konto
import no.nav.personopplysninger.consumer.norg2.dto.Norg2EnhetKontaktinfo
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.PersonaliaOgAdresser

object PersonaliaOgAdresserTransformer {

    fun toOutbound(
        pdlData: HentPersonQuery.Result,
        konto: Konto?,
        kodeverk: PersonaliaKodeverk,
        enhetKontaktInformasjon: Norg2EnhetKontaktinfo?
    ) =
        PersonaliaOgAdresser(
            personalia = PersoninfoTransformer.toOutbound(pdlData.person!!, konto, kodeverk),
            adresser = AdresseinfoTransformer.toOutbound(pdlData, kodeverk),
            enhetKontaktInformasjon = enhetKontaktInformasjon
        )
}