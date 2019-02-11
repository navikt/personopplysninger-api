package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Personalia
import no.nav.personopplysninger.features.personalia.dto.outbound.Personident
import no.nav.personopplysninger.features.personalia.kodeverk.*
import no.nav.tps.person.Kode
import no.nav.tps.person.Navn
import no.nav.tps.person.Personinfo
import org.slf4j.LoggerFactory


object PersoninfoTransformer {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun toOutbound(inbound: Personinfo): Personalia {

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
                spraak = inbound.spraak?.kode?.verdi?.let { Spraak.dekode(it) },
                epostadr = "TODO",
                personstatus = inbound.status?.kode?.verdi?.let { Personstatus.dekode(it) },
                statsborgerskap = inbound.statsborgerskap?.kode?.verdi?.let { StatsborgerskapFreg.dekode(it) },
                foedested = foedested(inbound.foedtIKommune?.verdi?.let  { Kommune.kommunenavn(it)  }, inbound.foedtILand),
                sivilstand = inbound.sivilstand?.kode?.verdi?.let { Sivilstand.dekode(it) },
                kjoenn = inbound.kjonn?.let { Kjoennstype.dekode(it) }
        )

    }

    private fun foedested(foedtIKommune: String?, foedtILand: Kode?): String? {
        val landnavn: String? = foedtILand?.verdi?.let { Landkode.dekode(it) }
        val names = listOfNotNull(foedtIKommune?.let  { Kommune.kommunenavn(it)  }, landnavn)
        return if (names.isEmpty()) null else names.joinToString(", ")
    }


}
