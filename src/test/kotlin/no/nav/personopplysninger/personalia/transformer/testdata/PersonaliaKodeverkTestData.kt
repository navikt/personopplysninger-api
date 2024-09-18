package no.nav.personopplysninger.personalia.transformer.testdata

import no.nav.personopplysninger.personalia.dto.AdresseKodeverk
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk

val defaultAdresseKodeverk = AdresseKodeverk(
    poststed = "poststed",
    land = "land",
    kommune = "kommune",
)

val defaultPersonaliaKodeverk = PersonaliaKodeverk(
    foedekommuneterm = "foedekommuneterm",
    foedelandterm = "foedelandterm",
    gtLandterm = "gtLandterm",
    statsborgerskaptermer = listOf("statsborgerskapterm"),
    utenlandskbanklandterm = "utenlandskbanklandterm",
    utenlandskbankvalutaterm = "utenlandskbankvalutaterm",
    kontaktadresseKodeverk = listOf(defaultAdresseKodeverk),
    bostedsadresseKodeverk = defaultAdresseKodeverk,
    deltBostedKodeverk = defaultAdresseKodeverk,
    oppholdsadresseKodeverk = listOf(defaultAdresseKodeverk),
)
