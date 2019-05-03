package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.arbeidsforhold.ArbeidsforholdService
import no.nav.security.oidc.api.ProtectedWithClaims
import no.nav.security.oidc.jaxrs.OidcRequestContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

private const val claimsIssuer = "selvbetjening"

@Component
@Path("/arbeidsforholdinnslag")
@ProtectedWithClaims(issuer = claimsIssuer, claimMap = ["acr=Level4"])
class ArbeidsforholdIdResource @Autowired constructor(private var arbeidsforholdIdService: ArbeidsforholdService) {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun hentPersonalia(): Response {
        val fodselsnr = hentFnrFraToken()
        val id = 0;
        val fssToken = "";
        //TODO hente id fra URL
        val arbeidsforhold = arbeidsforholdIdService.hentEttArbeidsforholdmedId(fodselsnr, id, fssToken)
        return Response
                .ok(arbeidsforhold)
                .build()
    }


    private fun hentFnrFraToken(): String {
        val context = OidcRequestContext.getHolder().oidcValidationContext
        return context.getClaims(claimsIssuer).claimSet.subject
    }
}