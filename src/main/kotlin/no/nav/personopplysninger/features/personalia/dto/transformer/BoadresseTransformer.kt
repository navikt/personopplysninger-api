package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Boadresse
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk

object BoadresseTransformer {
    fun toOutbound(inbound: no.nav.tps.person.Boadresse, kodeverk: PersonaliaKodeverk) = Boadresse(
            adresse = inbound.adresse,
            adressetillegg = inbound.adressetillegg,
            bydel = inbound.bydel,
            datoFraOgMed = inbound.datoFraOgMed,
            kommune = inbound.kommune?.let { kodeverk.bostedskommuneterm} ?: run {null},
            land = inbound.landkode?.let { kodeverk.landterm } ?: run {null},
            matrikkeladresse = inbound.matrikkeladresse?.let { MatrikkeladresseTransformer.toOutbound(it) },
            postnummer = inbound.postnummer,
            poststed = inbound.postnummer?.let { kodeverk.bostedpostnummerterm } ?: run {null},
            veiadresse = inbound.veiadresse?.let { VeiadresseTransformer.toOutbound(it) }
    )
}