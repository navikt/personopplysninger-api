package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Kilde
import no.nav.personopplysninger.features.personalia.dto.outbound.Navn

object NavnTransformer {
    fun toOutbound(inbound: no.nav.tps.person.Navn): Pair<Navn, Set<Kilde>> {
        val navn = Navn(
                fornavn = inbound.fornavn,
                mellomnavn = inbound.mellomnavn,
                slektsnavn = inbound.slektsnavn
        )
        val kilde: Set<Kilde> = inbound.kilde?.let { setOf(Kilde(it)) }.orEmpty()
        return Pair(navn, kilde)
    }
}