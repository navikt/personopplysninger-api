package no.nav.personopplysninger.features.ereg

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class EregOrganisasjon(

        val navn: Navn? = null

)