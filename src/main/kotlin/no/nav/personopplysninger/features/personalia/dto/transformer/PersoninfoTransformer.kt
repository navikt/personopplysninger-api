package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Kilde
import no.nav.personopplysninger.features.personalia.dto.outbound.Personalia
import no.nav.personopplysninger.features.personalia.dto.outbound.Tlfnr
import no.nav.tps.person.*


object PersoninfoTransformer {

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
                fnr = inbound.foedselsdato, // TODO Are: Hvor finner vi fnr? Se IN-735
                kontonr = inbound.kontonummer?.let { kontonr(it) },
                tlfnr = inbound.telefon?.let { tlfnr(it) },
                spraak = inbound.spraak?.let { it.kode?.verdi }, // TODO Are: Kodeverk. Husk Kilde
                epostadr = "TODO", // TODO Are: Hvor finner vi epostadr?
                personstatus = inbound.status?.let { it.kode?.verdi }, // TODO Are: Kodeverk. Husk kilde
                statsborgerskap = inbound.statsborgerskap?.let { it.kode?.verdi }, // TODO Are: Kodeverk. Husk kilde
                foedested = foedested(inbound.foedtIKommune, inbound.foedtILand), // TODO Are: Kodeverk.
                sivilstand = inbound.sivilstand?.let { it.kode?.verdi }, // TODO Are: Kodeverk. Husk kilde
                kjoenn = inbound.kjonn,
                datakilder = kilder
        )

    }

    private fun foedested(foedtIKommune: Kode?, foedtILand: Kode?): String? {
        val names = listOfNotNull(foedtIKommune?.verdi, foedtILand?.verdi)
        return if (names.isEmpty()) null else names.joinToString(", ")
    }


}
