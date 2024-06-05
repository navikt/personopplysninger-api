package no.nav.personopplysninger.personalia.transformer.testdata

import no.nav.personopplysninger.personalia.dto.AdresseKodeverk
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk

fun createDummyPersonaliaKodeverk(): PersonaliaKodeverk {
    return PersonaliaKodeverk(
        foedekommuneterm = "foedekommuneterm",
        foedelandterm = "foedelandterm",
        gtLandterm = "gtLandterm",
        statsborgerskaptermer = listOf("stasborgerskapterm"),
        utenlandskbanklandterm = "utenlandskbanklandterm",
        utenlandskbankvalutaterm = "utenlandskbankvalutaterm",
        kontaktadresseKodeverk = listOf(createDummyAdresseKodeverk()),
        bostedsadresseKodeverk = createDummyAdresseKodeverk(),
        deltBostedKodeverk = createDummyAdresseKodeverk(),
        oppholdsadresseKodeverk = listOf(createDummyAdresseKodeverk()),
    )
}

fun createDummyAdresseKodeverk(): AdresseKodeverk {
    return AdresseKodeverk(
        poststed = "poststed",
        land = "land",
        kommune = "kommune",
    )
}