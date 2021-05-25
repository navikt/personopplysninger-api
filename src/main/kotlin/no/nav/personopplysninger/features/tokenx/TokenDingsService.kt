package no.nav.personopplysninger.features.tokenx

import com.nimbusds.jose.JOSEObjectType
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.springframework.stereotype.Service

@Service
class TokenDingsService(
    val tokenXConsumer: TokenXConsumer
) {
//    private val rsaKey = RSAKey.parse(tokenXConfig.privateJwk)
//
//    fun clientAssertion(clientId: String, audience: String, rsaKey: RSAKey): String {
//        val now = Date.from(Instant.now())
//        return JWTClaimsSet.Builder()
//            .issuer(clientId)
//            .subject(clientId)
//            .audience(audience)
//            .issueTime(now)
//            .expirationTime(Date.from(Instant.now().plusSeconds(60)))
//            .jwtID(UUID.randomUUID().toString())
//            .notBeforeTime(now)
//            .build()
//            .sign(rsaKey)
//            .serialize()
//    }

    fun exchangeToken(): AccessTokenResponse {
        return tokenXConsumer.exchangeToken()
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
