package no.nav.personopplysninger.features.personalia.dto.transformer

import arrow.core.toOption
import no.nav.tps.person.Boadresse

object BoadresseTransformer {
    fun toOutbound(inbound: Boadresse) = no.nav.personopplysninger.features.personalia.dto.outbound.Boadresse(
            adresse = inbound.adresse,
            kommune = inbound.kommune,
            matrikkeladresse = inbound.matrikkeladresse.toOption().map { MatrikkeladresseTransformer.toOutbound(it) }.orNull(),
            postnummer = inbound.postnummer,
            veiadresse = inbound.veiadresse.toOption().map { VeiadresseTransformer.toOutbound(it) }.orNull()
    )
}