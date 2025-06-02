package no.nav.personopplysninger.endreopplysninger.idporten

import com.nimbusds.oauth2.sdk.pkce.CodeChallenge
import com.nimbusds.oauth2.sdk.pkce.CodeChallengeMethod
import com.nimbusds.oauth2.sdk.pkce.CodeVerifier

data class Pkce(
    val verifier: CodeVerifier = CodeVerifier(),
    val method: CodeChallengeMethod = CodeChallengeMethod.S256,
) {
    val challenge: CodeChallenge = CodeChallenge.compute(method, verifier)
}