package no.nav.personopplysninger.features.personalia.dto.transformer.testdata

import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk

fun createDummyPersonaliaKodeverk(): PersonaliaKodeverk {
    return PersonaliaKodeverk().apply {
        foedekommuneterm = "foedekommuneterm"
        foedelandterm = "foedelandterm"
        gtLandterm = "gtLandterm"
        statsborgerskapterm = "stasborgerskapterm"
        utenlandskbanklandterm = "utenlandskbanklandterm"
        utenlandskbankvalutaterm = "utenlandskbankvalutaterm"
        kontaktadressePostSted = "kontaktadressePostSted"
        kontaktadresseLand = "kontaktadresseLand"
        bostedsadressePostSted = "bostedsadressePostSted"
        bostedsadresseLand = "bostedsadresseLand"
        deltBostedPostSted = "deltBostedPostSted"
        deltBostedLand = "deltBostedLand"
        oppholdsadressePostSted = "oppholdsadressePostSted"
        oppholdsadresseLand = "oppholdsadresseLand"
    }
}