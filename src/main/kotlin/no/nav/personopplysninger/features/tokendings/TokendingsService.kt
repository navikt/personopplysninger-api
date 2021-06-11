package no.nav.personopplysninger.features.tokendings

import com.nimbusds.jose.JOSEObjectType
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import mu.withLoggingContext
import no.nav.personopplysninger.features.endreopplysninger.PersonMottakConsumer
import no.nav.personopplysninger.features.tokendings.domain.TokendingsToken
import no.nav.personopplysninger.features.tokendings.metadata.TokendingsMetadataConsumer
import no.nav.personopplysninger.tasks.EvickCacheTask
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class TokenDingsService(
    private val metadataConsumer: TokendingsMetadataConsumer,
    private val tokendingsConsumer: TokendingsConsumer
) {
    private val config: TokendingsContext = TokendingsContext()
    private val rsaKey = RSAKey.parse(config.privateJwk)
    private val log = LoggerFactory.getLogger(TokenDingsService::class.java)

    fun clientAssertion(clientId: String, audience: String, rsaKey: RSAKey): String {
        val now = Date.from(Instant.now())
        return JWTClaimsSet.Builder()
            .issuer(clientId)
            .subject(clientId)
            .audience(audience)
            .issueTime(now)
            .expirationTime(Date.from(Instant.now().plusSeconds(60)))
            .jwtID(UUID.randomUUID().toString())
            .notBeforeTime(now)
            .build()
            .sign(rsaKey)
            .serialize()
    }

    fun exchangeToken(token: String, targetApp: String): TokendingsToken {
        val metadata = metadataConsumer.hentMetadata()
        log.info("Token: $token") // todo fjern
        val jwt = clientAssertion(config.clientId, metadata.tokenEndpoint, rsaKey)

        return tokendingsConsumer.exchangeToken(token, jwt, targetApp)
    }
}

internal fun JWTClaimsSet.sign(rsaKey: RSAKey): SignedJWT =
    SignedJWT(
        JWSHeader.Builder(JWSAlgorithm.RS256)
            .keyID(rsaKey.keyID)
            .type(JOSEObjectType.JWT).build(),
        this
    ).apply {
        sign(RSASSASigner(rsaKey.toPrivateKey()))
    }
