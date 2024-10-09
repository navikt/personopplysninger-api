package no.nav.personopplysninger.personalia.mapper

import no.nav.pdl.generated.dto.hentpersonquery.Navn
import no.nav.pdl.generated.dto.hentpersonquery.Person
import no.nav.pdl.generated.dto.hentpersonquery.Telefonnummer
import no.nav.personopplysninger.consumer.kontoregister.dto.request.Konto
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.Personalia
import no.nav.personopplysninger.personalia.dto.outbound.Personident
import no.nav.personopplysninger.personalia.dto.outbound.Tlfnr
import no.nav.personopplysninger.personalia.extensions.stringValue

fun Person.toOutbound(konto: Konto?, kodeverk: PersonaliaKodeverk) = Personalia(
    fornavn = navn.firstOrNull()?.fornavn(),
    etternavn = navn.firstOrNull()?.etternavn,
    personident = folkeregisteridentifikator.first().let { Personident(it.identifikasjonsnummer, it.type) },
    kontonr = konto?.kontonummer.takeIf { konto?.utenlandskKontoInfo == null },
    tlfnr = telefonnummer.toTlfnr(),
    utenlandskbank = konto?.utenlandskKontoInfo?.let { konto.toOutbound(kodeverk) },
    statsborgerskap = kodeverk.statsborgerskaptermer,
    foedested = foedested(kodeverk.foedekommuneterm, kodeverk.foedelandterm),
    sivilstand = sivilstand.firstOrNull()?.type?.stringValue,
    kjoenn = kjoenn.firstOrNull()?.kjoenn?.stringValue,
    kontoregisterStatus = if (konto?.error == true) "ERROR" else "SUCCESS"
)

private fun Navn.fornavn() = if (mellomnavn == null) fornavn else "$fornavn $mellomnavn".trim()

private fun foedested(foedtIKommune: String?, foedtILand: String?): String? {
    val names = listOf(foedtIKommune, foedtILand).filter { !it.isNullOrEmpty() }
    return if (names.isEmpty()) null else names.joinToString(", ")
}

private fun List<Telefonnummer>.toTlfnr(): Tlfnr {
    val hoved = find { it.prioritet == 1 }
    val alternativ = find { it.prioritet == 2 }

    return Tlfnr(
        telefonHoved = hoved?.nummer,
        landskodeHoved = hoved?.landskode,
        telefonAlternativ = alternativ?.nummer,
        landskodeAlternativ = alternativ?.landskode
    )
}