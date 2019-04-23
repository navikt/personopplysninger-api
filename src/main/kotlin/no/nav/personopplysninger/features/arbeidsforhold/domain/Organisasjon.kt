package no.nav.personopplysninger.features.arbeidsforhold.domain

data class Organisasjon (

    /* Organisasjonsnummer fra Enhetsregisteret */
    val organisasjonsnummer: kotlin.String? = null,
    /* Type: Organisasjon eller Person */
    val type: Organisasjon.Type? = null
) {

    enum class Type(val value: kotlin.String){
        organisasjon("Organisasjon");
    }
}