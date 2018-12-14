package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.tps.person.Postadresse

object PostadresseTransformer {
    fun toOutbound(inbound: Postadresse) = no.nav.personopplysninger.features.personalia.dto.outbound.Postadresse(
            adresse1 = inbound.adresse1,
            adresse2 = inbound.adresse2,
            adresse3 = inbound.adresse3,
            land = inbound.land,
            postnummer = inbound.postnummer
    )
}