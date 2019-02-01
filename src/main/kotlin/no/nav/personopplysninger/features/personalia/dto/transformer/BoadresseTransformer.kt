package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Boadresse
import no.nav.personopplysninger.features.personalia.kodeverk.Kommune
import no.nav.personopplysninger.features.personalia.kodeverk.Landkode
import no.nav.personopplysninger.features.personalia.kodeverk.Postnummer

object BoadresseTransformer {
    fun toOutbound(inbound: no.nav.tps.person.Boadresse) = Boadresse(
            adresse = inbound.adresse,
            adressetillegg = inbound.adressetillegg,
            bydel = inbound.bydel,
            datoFraOgMed = inbound.datoFraOgMed,
            kommune = inbound.kommune?.let { Kommune.kommunenavn(it)},
            land = inbound.landkode?.let { Landkode.dekode(it) },
            matrikkeladresse = inbound.matrikkeladresse?.let { MatrikkeladresseTransformer.toOutbound(it) },
            postnummer = inbound.postnummer,
            poststed = inbound.postnummer?.let { Postnummer.poststed(it) },
            veiadresse = inbound.veiadresse?.let { VeiadresseTransformer.toOutbound(it) }
    )
}