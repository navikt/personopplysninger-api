package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.consumer.kodeverk.domain.PersonaliaKodeverk
import no.nav.personopplysninger.consumer.kontoregister.domain.Konto
import no.nav.personopplysninger.consumer.pdl.dto.PdlPerson
import no.nav.personopplysninger.consumer.pdl.dto.personalia.PdlNavn
import no.nav.personopplysninger.features.personalia.dto.outbound.Personalia
import no.nav.personopplysninger.features.personalia.dto.outbound.Personident


object PersoninfoTransformer {

    fun toOutbound(pdlPerson: PdlPerson, konto: Konto?, kodeverk: PersonaliaKodeverk): Personalia {

        fun fornavn(navn: PdlNavn): String =
            if (navn.mellomnavn == null) navn.fornavn
            else {
                "${navn.fornavn} ${navn.mellomnavn}".trim()
            }

        return Personalia(
            fornavn = pdlPerson.navn.firstOrNull()?.let { fornavn(it) },
            etternavn = pdlPerson.navn.firstOrNull()?.etternavn,
            personident = pdlPerson.folkeregisteridentifikator.firstOrNull()
                .let { Personident(it!!.identifikasjonsnummer, it.type) },
            kontonr = konto?.kontonummer,
            tlfnr = pdlPerson.telefonnummer.toTlfnr(),
            utenlandskbank = konto?.utenlandskKontoInfo?.let { UtenlandskBankTransformer.toOutbound(konto, kodeverk) },
            statsborgerskap = kodeverk.statsborgerskapterm,
            foedested = foedested(kodeverk.foedekommuneterm, kodeverk.foedelandterm),
            sivilstand = pdlPerson.sivilstand.firstOrNull()?.type?.beskrivelse,
            kjoenn = pdlPerson.kjoenn.firstOrNull()?.kjoenn?.beskrivelse,
        )
    }

    private fun foedested(foedtIKommune: String?, foedtILand: String?): String? {
        val names = listOfNotNull(foedtIKommune, foedtILand).filter { it.isNotEmpty() }
        return if (names.isEmpty()) null else names.joinToString(", ")
    }
}
