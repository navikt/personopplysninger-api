package no.nav.personopplysninger.features.arbeidsforhold.domain

data class Person (

    /* Akt&oslash;r-id */
    val aktoerId: kotlin.String? = null,
    /* Gjeldende offentlig ident */
    val offentligIdent: kotlin.String? = null,
    /* Type: Organisasjon eller Person */
    val type: kotlin.String? = null
)