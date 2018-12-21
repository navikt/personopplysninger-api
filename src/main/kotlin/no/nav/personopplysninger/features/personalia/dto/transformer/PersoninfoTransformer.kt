package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Kilde
import no.nav.personopplysninger.features.personalia.dto.outbound.Personalia
import no.nav.personopplysninger.features.personalia.dto.outbound.Tlfnr
import no.nav.tps.person.Kontonummer
import no.nav.tps.person.Navn
import no.nav.tps.person.Personinfo
import no.nav.tps.person.Telefoninfo


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
                    listOfNotNull(inbound.fornavn, inbound.mellomnavn).joinToString(separator = " ")
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
                fnr = inbound.foedselsdato, // TODO Are: Hvor finner vi fnr?
                kontonr = inbound.kontonummer?.let { kontonr(it) },
                tlfnr = inbound.telefon?.let { tlfnr(it) },
                spraak = inbound.spraak?.let { it.kode?.let { it.verdi } }, // TODO Are: Kodeverk. Husk Kilde
                epostadr = "TODO", // TODO Are: Hvor finner vi epostadr?
                // TODO Are: Alle felter i GUI
                datakilder = kilder
        )

    }


}
