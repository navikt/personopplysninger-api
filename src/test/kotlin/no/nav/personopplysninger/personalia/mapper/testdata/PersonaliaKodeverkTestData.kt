package no.nav.personopplysninger.personalia.mapper.testdata

import no.nav.personopplysninger.personalia.dto.AdresseKodeverk
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk

val defaultAdresseKodeverk = AdresseKodeverk(
    poststed = "poststed",
    land = "land",
    kommune = "kommune",
)

val defaultPersonaliaKodeverk = PersonaliaKodeverk(
    foedelandterm = "foedelandterm",
    gtLandterm = "gtLandterm",
    foedekommuneterm = "foedekommuneterm",
    statsborgerskaptermer = listOf("statsborgerskapterm"),
    utenlandskbanklandterm = "utenlandskbanklandterm",
    utenlandskbankvalutaterm = "utenlandskbankvalutaterm",
    kontaktadresseKodeverk = listOf(defaultAdresseKodeverk),
    bostedsadresseKodeverk = defaultAdresseKodeverk,
    deltBostedKodeverk = defaultAdresseKodeverk,
    oppholdsadresseKodeverk = listOf(defaultAdresseKodeverk),
)
