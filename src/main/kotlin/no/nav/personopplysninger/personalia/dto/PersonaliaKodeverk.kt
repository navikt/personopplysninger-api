package no.nav.personopplysninger.personalia.dto

class PersonaliaKodeverk(
    val foedelandterm: String? = null,
    val foedekommuneterm: String? = null,
    val statsborgerskaptermer: List<String> = emptyList(),
    val utenlandskbanklandterm: String? = null,
    val utenlandskbankvalutaterm: String? = null,
    val kontaktadresseKodeverk: List<AdresseKodeverk> = emptyList(),
    val bostedsadresseKodeverk: AdresseKodeverk = AdresseKodeverk(),
    val deltBostedKodeverk: AdresseKodeverk = AdresseKodeverk(),
    val oppholdsadresseKodeverk: List<AdresseKodeverk> = emptyList(),
)