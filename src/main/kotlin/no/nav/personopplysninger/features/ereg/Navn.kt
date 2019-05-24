package no.nav.personopplysninger.features.ereg

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Navn(

        val navnelinje1: kotlin.String? = null,
        val navnelinje2: kotlin.String? = null,
        val navnelinje3: kotlin.String? = null,
        val navnelinje4: kotlin.String? = null,
        val navnelinje5: kotlin.String? = null

)