package no.nav.personopplysninger.features.personalia.pdl.dto.navn

import no.nav.personopplysninger.features.personalia.pdl.PDLRequest
import no.nav.personopplysninger.features.personalia.pdl.compactJson
import no.nav.personopplysninger.features.personalia.pdl.dto.QueryVariables

class NavnRequest(override val variables: QueryVariables) : PDLRequest {

    override val query: String
        get() = """
            query (${"$"}ident: ID!) {
                person: hentPerson(ident: ${"$"}ident) {
                    $navnQueryPart
                } 
            }
        """.compactJson()
}

const val navnQueryPart = "navn {fornavn, mellomnavn, etternavn}"

fun createNavnRequest(ident: String): NavnRequest {
    return NavnRequest(QueryVariables(ident))
}