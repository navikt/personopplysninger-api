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
        kontaktadresseKommune = "kontaktadresseKommune"
        bostedsadressePostSted = "bostedsadressePostSted"
        bostedsadresseLand = "bostedsadresseLand"
        bostedsadresseKommune = "bostedsadresseKommune"
        deltBostedPostSted = "deltBostedPostSted"
        deltBostedLand = "deltBostedLand"
        deltBostedKommune = "deltBostedKommune"
        oppholdsadressePostSted = "oppholdsadressePostSted"
        oppholdsadresseLand = "oppholdsadresseLand"
        oppholdsadresseKommune = "oppholdsadresseKommune"
    }
}