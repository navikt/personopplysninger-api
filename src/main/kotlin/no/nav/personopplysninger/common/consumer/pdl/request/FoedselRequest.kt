package no.nav.personopplysninger.common.consumer.pdl.request

import kotlinx.serialization.Serializable

@Serializable
class FoedselRequest(override val variables: QueryVariables) : PDLRequest {

    override val query: String = """
            query (${"$"}ident: ID!) {
                person: hentPerson(ident: ${"$"}ident) {
                    $foedselQueryPart
                }
            }
        """.compactJson()
}

fun createFoedselRequest(ident: String): FoedselRequest {
    return FoedselRequest(QueryVariables(ident))
}