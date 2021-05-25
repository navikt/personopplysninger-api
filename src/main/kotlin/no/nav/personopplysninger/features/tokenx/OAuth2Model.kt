package no.nav.personopplysninger.features.tokenx

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
data class OauthServerConfigurationMetadata(
    @JsonProperty(value = "issuer", required = true) val issuer: String,
    @JsonProperty(value = "token_endpoint", required = true) val tokenEndpoint: String,
    @JsonProperty(value = "jwks_uri", required = true) val jwksUri: String,
    @JsonProperty(value = "authorization_endpoint", required = false) var authorizationEndpoint: String = ""
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class AccessTokenResponse(
    @JsonProperty(value = "access_token", required = true) val accessToken: String,
    @JsonProperty(value = "issued_token_type", required = true) val issuedTokenType: String,
    @JsonProperty(value = "token_type", required = true) val tokenType: String,
    @JsonProperty(value = "expires_in", required = true) val expiresIn: Int
)
