package no.nav.personopplysninger.features.personalia.dto.transformer.testdata

import no.nav.personopplysninger.consumer.kodeverk.domain.AdresseKodeverk
import no.nav.personopplysninger.consumer.kodeverk.domain.PersonaliaKodeverk

fun createDummyPersonaliaKodeverk(): PersonaliaKodeverk {
    return PersonaliaKodeverk().apply {
        foedekommuneterm = "foedekommuneterm"
        foedelandterm = "foedelandterm"
        gtLandterm = "gtLandterm"
        statsborgerskaptermer = listOf("stasborgerskapterm")
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