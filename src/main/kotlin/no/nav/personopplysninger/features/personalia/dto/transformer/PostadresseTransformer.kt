package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Postadresse
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk


object PostadresseTransformer {
    fun toOutbound(inbound: no.nav.tps.person.Postadresse, kodeverk: PersonaliaKodeverk) = Postadresse(
            adresse1 = inbound.adresse1,
            adresse2 = inbound.adresse2,
            adresse3 = inbound.adresse3,
            datoFraOgMed = inbound.datoFraOgMed,
            land = kodeverk.postadresselandterm ?: run {null},
            postnummer = inbound.postnummer,
            poststed = inbound.postnummer?.let { kodeverk.postnummerterm} ?: run {null}
    )
}