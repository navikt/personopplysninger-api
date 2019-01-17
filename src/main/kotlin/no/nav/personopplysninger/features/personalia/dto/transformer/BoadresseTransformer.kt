package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Boadresse
import no.nav.personopplysninger.features.personalia.kodeverk.Postnummer

object BoadresseTransformer {
    fun toOutbound(inbound: no.nav.tps.person.Boadresse) = Boadresse(
            adresse = inbound.adresse,
            adressetillegg = inbound.adressetillegg,
            bydel = inbound.bydel,
            datoFraOgMed = inbound.datoFraOgMed,
            kommune = inbound.kommune,
            landkode = inbound.landkode,
            matrikkeladresse = inbound.matrikkeladresse?.let { MatrikkeladresseTransformer.toOutbound(it) },
            postnummer = inbound.postnummer,
            poststed = inbound.postnummer?.let { Postnummer.poststed(it) },
            veiadresse = inbound.veiadresse?.let { VeiadresseTransformer.toOutbound(it) }
    )
}