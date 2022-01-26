package no.nav.personopplysninger.features.personalia.dto.transformer.testdata

import no.nav.personopplysninger.features.personalia.kodeverk.AdresseKodeverk
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk

fun createDummyPersonaliaKodeverk(): PersonaliaKodeverk {
    return PersonaliaKodeverk().apply {
        foedekommuneterm = "foedekommuneterm"
        foedelandterm = "foedelandterm"
        gtLandterm = "gtLandterm"
        statsborgerskapterm = "stasborgerskapterm"
        utenlandskbanklandterm = "utenlandskbanklandterm"
        utenlandskbankvalutaterm = "utenlandskbankvalutaterm"
        kontaktadresseKodeverk = listOf(createDummyAdresseKodeverk())
        bostedsadresseKodeverk = createDummyAdresseKodeverk()
        deltBostedKodeverk = createDummyAdresseKodeverk()
        oppholdsadresseKodeverk = listOf(createDummyAdresseKodeverk())
    }
}

fun createDummyAdresseKodeverk(): AdresseKodeverk {
    return AdresseKodeverk().apply {
        poststed = "poststed"
        land = "land"
        kommune = "kommune"
    }
}