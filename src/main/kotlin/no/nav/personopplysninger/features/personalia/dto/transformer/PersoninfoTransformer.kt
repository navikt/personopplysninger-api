package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Personalia
import no.nav.personopplysninger.features.personalia.dto.outbound.Personident
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlPerson
import no.nav.personopplysninger.features.personalia.pdl.dto.personalia.PdlNavn
import no.nav.tps.person.Navn
import no.nav.tps.person.Personinfo


object PersoninfoTransformer {

    fun toOutbound(inbound: Personinfo, pdlPerson: PdlPerson, kodeverk: PersonaliaKodeverk): Personalia {

        fun fornavn(inbound: Navn): String? =
            if (inbound.fornavn == null && inbound.mellomnavn == null) null
            else {
                "${inbound.fornavn ?: ""} ${inbound.mellomnavn ?: ""}".trim()
            }

        return Personalia(
            fornavn = inbound.navn?.let { fornavn(it) },
            etternavn = inbound.navn?.slektsnavn,
            personident = inbound.ident?.let { Personident(it, inbound.identtype?.verdi) },
            kontonr = inbound.kontonummer?.nummer,
            tlfnr = pdlPerson.telefonnummer.toTlfnr(),
            utenlandskbank = inbound.utenlandskBank?.let { UtenlandskBankTransformer.toOutbound(it, kodeverk) },
            statsborgerskap = inbound.statsborgerskap?.kode?.verdi?.let { kodeverk.stasborgerskapterm },
            foedested = foedested(inbound.foedtIKommune?.verdi?.let { kodeverk.foedekommuneterm }, kodeverk.landterm),
            sivilstand = inbound.sivilstand?.kode?.verdi?.let { kodeverk.sivilstandterm },
            kjoenn = inbound.kjonn?.let { kodeverk.kjonnterm }
        )
    }

    fun toOutboundMigrert(tpsPerson: Personinfo, pdlPerson: PdlPerson, kodeverk: PersonaliaKodeverk): Personalia {

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
            kontonr = tpsPerson.kontonummer?.nummer,
            tlfnr = pdlPerson.telefonnummer.toTlfnr(),
            utenlandskbank = tpsPerson.utenlandskBank?.let { UtenlandskBankTransformer.toOutbound(it, kodeverk) },
            statsborgerskap = kodeverk.stasborgerskapterm,
            foedested = foedested(kodeverk.foedekommuneterm, kodeverk.landterm),
            sivilstand = pdlPerson.sivilstand.firstOrNull()?.type?.beskrivelse, //todo: bruk siste sivilstatus
            kjoenn = pdlPerson.kjoenn.firstOrNull()?.kjoenn?.beskrivelse,
        )
    }

    private fun foedested(foedtIKommune: String?, foedtILand: String?): String? {
        val landnavn: String? = foedtILand
        val names = listOfNotNull(foedtIKommune, landnavn)
        return if (names.isEmpty()) null else names.joinToString(", ")
    }
}
