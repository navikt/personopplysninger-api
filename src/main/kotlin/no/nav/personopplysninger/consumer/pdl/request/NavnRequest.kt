package no.nav.personopplysninger.consumer.pdl.request

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

fun createNavnRequest(ident: String): NavnRequest {
    return NavnRequest(QueryVariables(ident))
}