package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.tps.person.UtenlandskAdresse

object UtenlandskAdresseTransformer {
    fun toOutbound(inbound: UtenlandskAdresse, kodeverk: PersonaliaKodeverk) = no.nav.personopplysninger.features.personalia.dto.outbound.UtenlandskAdresse(
            adresse1 = inbound.adresse1,
            adresse2 = inbound.adresse2,
            adresse3 = inbound.adresse3,
            datoFraOgMed = inbound.datoFraOgMed,
            datoTilOgMed = inbound.datoTilOgMed,
            land = inbound.land?.let { kodeverk.utenlandsadresseterm }
    )
}