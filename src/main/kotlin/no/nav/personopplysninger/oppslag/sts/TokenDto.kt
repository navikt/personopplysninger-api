package no.nav.personopplysninger.oppslag.sts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class TokenDto (
    val access_token: String = ""
)
