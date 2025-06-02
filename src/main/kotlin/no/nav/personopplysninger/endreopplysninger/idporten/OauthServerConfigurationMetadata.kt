package no.nav.personopplysninger.endreopplysninger.idporten

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
internal data class OauthServerConfigurationMetadata(
    @JsonNames("issuer") val issuer: String,
    @JsonNames("token_endpoint") val tokenEndpoint: String,
    @JsonNames("jwks_uri") val jwksUri: String,
    @JsonNames("authorization_endpoint") val authorizationEndpoint: String = ""
)