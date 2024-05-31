package no.nav.personopplysninger.personalia.transformer

import no.nav.pdl.generated.dto.hentpersonquery.Navn
import no.nav.pdl.generated.dto.hentpersonquery.Person
import no.nav.personopplysninger.common.consumer.kontoregister.dto.outbound.Konto
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.Personalia
import no.nav.personopplysninger.personalia.dto.outbound.Personident


object PersoninfoTransformer {

    fun toOutbound(pdlPerson: Person, konto: Konto?, kodeverk: PersonaliaKodeverk): Personalia {

        fun fornavn(navn: Navn): String =
            if (navn.mellomnavn == null) navn.fornavn
            else {
                "${navn.fornavn} ${navn.mellomnavn}".trim()
            }

        return Personalia(
            fornavn = pdlPerson.navn.firstOrNull()?.let { fornavn(it) },
            etternavn = pdlPerson.navn.firstOrNull()?.etternavn,
            personident = pdlPerson.folkeregisteridentifikator.first()
                .let { Personident(it.identifikasjonsnummer, it.type) },
            kontonr = if (konto?.utenlandskKontoInfo == null) konto?.kontonummer else null,
            tlfnr = pdlPerson.telefonnummer.toTlfnr(),
            utenlandskbank = konto?.utenlandskKontoInfo?.let { UtenlandskBankTransformer.toOutbound(konto, kodeverk) },
            statsborgerskap = kodeverk.statsborgerskaptermer,
            foedested = foedested(kodeverk.foedekommuneterm, kodeverk.foedelandterm),
            sivilstand = pdlPerson.sivilstand.firstOrNull()?.type?.name, //todo: må ha mapping her
            kjoenn = pdlPerson.kjoenn.firstOrNull()?.kjoenn?.name, //todo: må ha mapping her
            kontoregisterStatus = if (konto?.error == true) "ERROR" else "SUCCESS"
        )
    }

    private fun foedested(foedtIKommune: String?, foedtILand: String?): String? {
        val names = listOfNotNull(foedtIKommune, foedtILand).filter { it.isNotEmpty() }
        return if (names.isEmpty()) null else names.joinToString(", ")
    }
}
