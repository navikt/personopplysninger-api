package no.nav.personopplysninger.personalia.transformer

import no.nav.pdl.generated.dto.HentPersonQuery
import no.nav.personopplysninger.consumer.kontoregister.dto.request.Konto
import no.nav.personopplysninger.consumer.norg2.dto.Norg2EnhetKontaktinfo
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.PersonaliaOgAdresser

fun HentPersonQuery.Result.toOutbound(
    konto: Konto?,
    kodeverk: PersonaliaKodeverk,
    enhetKontaktInformasjon: Norg2EnhetKontaktinfo?
): PersonaliaOgAdresser {
    requireNotNull(person) { "Person kan ikke være null" }
    return PersonaliaOgAdresser(
        personalia = person.toOutboundPersonalia(konto, kodeverk),
        adresser = person.toOutboundAdresser(kodeverk),
        enhetKontaktInformasjon = enhetKontaktInformasjon
    )
}
