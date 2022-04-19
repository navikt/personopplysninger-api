package no.nav.personopplysninger.consumer.tokendings.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class TokendingsToken(
    @JsonProperty(value = "access_token", required = true) val accessToken: String,
    @JsonProperty(value = "issued_token_type", required = true) val issuedTokenType: String,
    @JsonProperty(value = "token_type", required = true) val tokenType: String,
    @JsonProperty(value = "expires_in", required = true) val expiresIn: Int
)