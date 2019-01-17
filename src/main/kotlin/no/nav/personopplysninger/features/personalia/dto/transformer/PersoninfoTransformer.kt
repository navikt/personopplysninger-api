package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Kilde
import no.nav.personopplysninger.features.personalia.dto.outbound.Personalia
import no.nav.personopplysninger.features.personalia.dto.outbound.Personident
import no.nav.personopplysninger.features.personalia.dto.outbound.Tlfnr
import no.nav.personopplysninger.features.personalia.kodeverk.Kjoennstype
import no.nav.personopplysninger.features.personalia.kodeverk.Landkode
import no.nav.personopplysninger.features.personalia.kodeverk.Personstatus
import no.nav.personopplysninger.features.personalia.kodeverk.Sivilstand
import no.nav.tps.person.*
import org.slf4j.LoggerFactory


object PersoninfoTransformer {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun toOutbound(inbound: Personinfo): Personalia {
        val kilder: MutableSet<Kilde> = mutableSetOf()

        fun addKilde(kilde: String?): Unit {
            kilde?.let { kilder.add(Kilde(it)) }
        }

        fun fornavn(inbound: Navn): String? =
                if (inbound.fornavn == null && inbound.mellomnavn == null) null
                else {
                    addKilde(inbound.kilde)
                    "${inbound.fornavn ?: ""} ${inbound.mellomnavn ?: ""}".trim()
                }

        fun etternavn(inbound: Navn): String? =
                inbound.slektsnavn?.let {
                    addKilde(inbound.kilde)
                    it
                }

        fun kontonr(inbound: Kontonummer): String? =
                inbound.nummer?.let {
                    addKilde(inbound.kilde)
                    it
                }

        fun tlfnr(inbound: Telefoninfo): Tlfnr {
            val outbound = TelefoninfoTransformer.toOutbound(inbound)
            kilder.addAll(outbound.datakilder)
            return outbound
        }


        return Personalia(
                fornavn = inbound.navn?.let { fornavn(it) },
                etternavn = inbound.navn?.let { etternavn(it) },
                personident = inbound.ident?.let { Personident(it, inbound.identtype?.verdi) },
                kontonr = inbound.kontonummer?.let { kontonr(it) },
                tlfnr = inbound.telefon?.let { tlfnr(it) },
                spraak = inbound.spraak?.let { it.kode?.verdi }, // TODO Are: Kodeverk. Husk Kilde
                epostadr = "TODO", // TODO Are: Hvor finner vi epostadr?
                personstatus = inbound.status?.kode?.verdi?.let { Personstatus.dekode(it) }, // TODO Are: Kodeverk. Husk kilde
                statsborgerskap = inbound.statsborgerskap?.let { it.kode?.verdi }, // TODO Are: Kodeverk. Husk kilde
                foedested = foedested(inbound.foedtIKommune, inbound.foedtILand), // TODO Are: Kodeverk.
                sivilstand = inbound.sivilstand?.kode?.verdi?.let { Sivilstand.dekode(it) }, // TODO Are: Kodeverk. Husk kilde
                kjoenn = inbound.kjonn?.let { Kjoennstype.dekode(it) },
                datakilder = kilder
        )

    }

    private fun foedested(foedtIKommune: Kode?, foedtILand: Kode?): String? {
        val landnavn: String? = foedtILand?.verdi?.let { Landkode.dekode(it) }
        val names = listOfNotNull(foedtIKommune?.verdi, landnavn)
        return if (names.isEmpty()) null else names.joinToString(", ")
    }


}
