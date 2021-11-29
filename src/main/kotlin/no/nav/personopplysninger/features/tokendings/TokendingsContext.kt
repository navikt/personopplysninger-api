package no.nav.personopplysninger.features.tokendings

import com.natpryce.konfig.*

private val config: Configuration =
    ConfigurationProperties.systemProperties() overriding EnvironmentVariables()

internal class TokendingsContext(
    val wellKnownUrl: String = config[Key("token.x.well.known.url", stringType)],
    val clientId: String = config[Key("token.x.client.id", stringType)],
    val privateJwk: String = config[Key("token.x.private.jwk", stringType)]
)
