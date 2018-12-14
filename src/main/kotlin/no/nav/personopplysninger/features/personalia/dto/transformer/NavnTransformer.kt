package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.tps.person.Navn

object NavnTransformer {
    fun toOutbound(inbound: Navn) = no.nav.personopplysninger.features.personalia.dto.outbound.Navn(
            fornavn = inbound.fornavn,
            mellomnavn = inbound.mellomnavn,
            slektsnavn = inbound.slektsnavn,
            kilde = inbound.kilde
    )
}