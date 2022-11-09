package no.nav.personopplysninger.common.consumer.pdl.request

import kotlinx.serialization.Serializable

@Serializable
class TelefonRequest(override val variables: QueryVariables) : PDLRequest {

    override val query: String = """
            query (${"$"}ident: ID!) {
                person: hentPerson(ident: ${"$"}ident) {
                    $telefonQueryPart
                } 
            }
        """.compactJson()
}

fun createTelefonRequest(ident: String): TelefonRequest {
    return TelefonRequest(QueryVariables(ident))
}