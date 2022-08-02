package no.nav.personopplysninger.consumer.kodeverk.domain

class PersonaliaKodeverk {

    var foedelandterm: String? = null
    var gtLandterm: String? = null
    var foedekommuneterm: String? = null
    var statsborgerskaptermer: List<String> = arrayListOf()
    var utenlandskbanklandterm : String? = null
    var utenlandskbankvalutaterm : String? = null
    var kontaktadresseKodeverk: List<AdresseKodeverk> = arrayListOf()
    var bostedsadresseKodeverk: AdresseKodeverk = AdresseKodeverk()
    var deltBostedKodeverk: AdresseKodeverk = AdresseKodeverk()
    var oppholdsadresseKodeverk: List<AdresseKodeverk> = arrayListOf()
}

