package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Personalia
import no.nav.personopplysninger.features.personalia.dto.outbound.Personident
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.tps.person.Navn
import no.nav.tps.person.Personinfo
import org.slf4j.LoggerFactory


object PersoninfoTransformer {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun toOutbound(inbound: Personinfo, kodeverk: PersonaliaKodeverk): Personalia {

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
                tlfnr = inbound.telefon?.let { TelefoninfoTransformer.toOutbound(it) },
                utenlandskbank = inbound.utenlandskBank?.let { UtenlandskBankTransformer.toOutbound(it, kodeverk)},
                spraak = inbound.spraak?.kode?.verdi?.let { kodeverk.spraakterm },
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
