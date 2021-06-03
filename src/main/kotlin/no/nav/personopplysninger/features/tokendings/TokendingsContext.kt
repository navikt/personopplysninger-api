package no.nav.personopplysninger.features.tokendings

import com.natpryce.konfig.*
import kotlinx.coroutines.runBlocking
import no.nav.personopplysninger.features.tokendings.domain.TokendingsMetaConfiguration

// todo dynamisk audience
private const val audience = "dev-sbs:default:personopplysninger-api"
private val config: Configuration =
    ConfigurationProperties.systemProperties() overriding EnvironmentVariables()

internal class TokendingsContext(
    val wellKnownUrl: String = config[Key("token.x.well.known.url", stringType)],
    val clientId: String = config[Key("token.x.client.id", stringType)],
    val privateJwk: String = config[Key("token.x.private.jwk", stringType)],
    val gcpAudience: String = config.getOrElse(Key("client.gcp.audience", stringType), audience),
    val onpremAudience: String = config.getOrElse(Key("client.onprem.audience", stringType), audience)
)
