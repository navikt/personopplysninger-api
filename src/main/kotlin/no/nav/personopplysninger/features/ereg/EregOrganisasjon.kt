package no.nav.personopplysninger.features.ereg

data class EregOrganisasjon (

    /* Organisasjonsnavn fra Enhetsregisteret */
    val redigertnavn: kotlin.String? = null,
    /* Type: Organisasjon eller Person */
    val navn: Type? = null
) {

    enum class Type(val value: kotlin.String){
        redigertnavn("redigertnavn");
    }
}