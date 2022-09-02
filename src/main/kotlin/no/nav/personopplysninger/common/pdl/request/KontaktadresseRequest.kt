package no.nav.personopplysninger.common.pdl.request

import kotlinx.serialization.Serializable

@Serializable
class KontaktadresseRequest(override val variables: QueryVariables) : PDLRequest {

    override val query: String = """
            query (${"$"}ident: ID!) {
                person: hentPerson(ident: ${"$"}ident) {
                    $kontaktadresseQueryPart
                } 
            }
        """.compactJson()
}

fun createKontaktadresseRequest(ident: String): PDLRequest {
    return KontaktadresseRequest(QueryVariables(ident))
}