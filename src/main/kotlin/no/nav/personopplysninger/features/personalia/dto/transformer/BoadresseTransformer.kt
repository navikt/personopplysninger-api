package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.tps.person.Boadresse

object BoadresseTransformer {
    fun toOutbound(inbound: Boadresse) = no.nav.personopplysninger.features.personalia.dto.outbound.Boadresse(
            adresse = inbound.adresse,
            kommune = inbound.kommune,
            matrikkeladresse = MatrikkeladresseTransformer.toOutbound(inbound.matrikkeladresse!!),
            postnummer = inbound.postnummer,
            veiadresse = VeiadresseTransformer.toOutbound(inbound.veiadresse!!)
    )
}