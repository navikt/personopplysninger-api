package no.nav.personopplysninger.features.personalia.dto.transformer.testdata

import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk

object PersonaliaKodeverkObjectMother {
    fun dummyStedOgLandKode(): PersonaliaKodeverk {
        return PersonaliaKodeverk().apply {
            kontaktadresseLand = "DM"
            kontaktadressePostSted = "Dummy"
        }
    }
}