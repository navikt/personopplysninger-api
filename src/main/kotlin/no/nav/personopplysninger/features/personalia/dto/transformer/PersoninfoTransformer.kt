package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Personalia
import no.nav.personopplysninger.features.personalia.dto.outbound.Personident
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.PdlPerson
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
                utenlandskbank = inbound.utenlandskBank?.let { UtenlandskBankTransformer.toOutbound(it, kodeverk)},
                personstatus = inbound.status?.kode?.verdi?.let { kodeverk.statusterm },
                statsborgerskap = inbound.statsborgerskap?.kode?.verdi?.let { kodeverk.stasborgerskapterm },
                foedested = foedested(inbound.foedtIKommune?.verdi?.let  { kodeverk.foedekommuneterm  }, kodeverk.landterm),
                sivilstand = inbound.sivilstand?.kode?.verdi?.let { kodeverk.sivilstandterm },
                kjoenn = inbound.kjonn?.let { kodeverk.kjonnterm }
        )

    }

    private fun foedested(foedtIKommune: String?, foedtILand: String?): String? {
        val landnavn: String? = foedtILand?.let { it }
        val names = listOfNotNull(foedtIKommune, landnavn)
        return if (names.isEmpty()) null else names.joinToString(", ")
    }
}
