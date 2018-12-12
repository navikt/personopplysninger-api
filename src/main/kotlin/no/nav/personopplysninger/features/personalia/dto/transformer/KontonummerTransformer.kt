package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.tps.person.Kontonummer

object KontonummerTransformer {
    fun toOutbound(inbound: Kontonummer) = no.nav.personopplysninger.features.personalia.dto.outbound.Kontonummer(
            nummer = inbound.nummer
    )
}