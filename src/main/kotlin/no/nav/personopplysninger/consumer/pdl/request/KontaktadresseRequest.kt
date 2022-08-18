package no.nav.personopplysninger.consumer.pdl.request

import kotlinx.serialization.Serializable

@Serializable
class KontaktadresseRequest(override val variables: QueryVariables) : PDLRequest {

    override val query: String
        get() = """
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