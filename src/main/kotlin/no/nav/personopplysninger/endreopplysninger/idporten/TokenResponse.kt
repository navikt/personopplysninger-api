package no.nav.personopplysninger.endreopplysninger.idporten

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
internal data class TokenResponse(
    @JsonNames("id_token")
    val idToken: String,
    @JsonNames("access_token")
    val accessToken: String,
    @JsonNames("expires_in")
    val expiresIn: Int,
    @JsonNames("token_type")
    val tokenType: String,
)