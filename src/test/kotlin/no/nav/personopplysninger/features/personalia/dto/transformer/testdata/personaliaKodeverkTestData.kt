package no.nav.personopplysninger.features.personalia.dto.transformer.testdata

import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk

fun createDummyPersonaliaKodeverk(): PersonaliaKodeverk {
    return PersonaliaKodeverk().apply {
        landterm = "landterm"
        gtLandterm = "gtLandterm"
        foedekommuneterm = "foedekommuneterm"
        bostedskommuneterm = "bostedskommuneterm"
        spraakterm = "spraakterm"
        statsborgerskapterm = "stasborgerskapterm"
        postnummerterm = "postnummerterm"
        bostedpostnummerterm = "bostedpostnummerterm"
        tilleggsadressepostnummerterm = "tilleggsadressepostnummerterm"
        utenlandskadresseterm = "utenlandskadresseterm"
        postadresselandterm = "postadresselandterm"
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