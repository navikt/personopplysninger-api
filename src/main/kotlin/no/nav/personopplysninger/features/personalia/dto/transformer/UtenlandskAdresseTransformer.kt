package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.tps.person.UtenlandskAdresse

object UtenlandskAdresseTransformer {
    fun toOutbound(inbound: UtenlandskAdresse) = no.nav.personopplysninger.features.personalia.dto.outbound.UtenlandskAdresse(
            adresse1 = inbound.adresse1,
            adresse2 = inbound.adresse2,
            adresse3 = inbound.adresse3,
            land = inbound.land
    )
}