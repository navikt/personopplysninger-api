package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.tps.person.Boadresse
import arrow.core.Option
import arrow.core.getOrElse

object BoadresseTransformer {
    fun toOutbound(inbound: Boadresse) = no.nav.personopplysninger.features.personalia.dto.outbound.Boadresse(
            adresse = inbound.adresse,
            kommune = inbound.kommune,
            matrikkeladresse = Option.fromNullable(inbound.matrikkeladresse).map { MatrikkeladresseTransformer.toOutbound(it) }.getOrElse { null },
            postnummer = inbound.postnummer,
            veiadresse = Option.fromNullable(inbound.veiadresse).map { VeiadresseTransformer.toOutbound(it) }.getOrElse { null }
    )
}