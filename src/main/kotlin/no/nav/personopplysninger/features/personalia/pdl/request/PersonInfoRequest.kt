package no.nav.personopplysninger.features.personalia.pdl.request

class PersonInfoRequest(override val variables: QueryVariables) : PDLRequest {

    override val query: String
        get() = """
            query (${"$"}ident: ID!) {
                person: hentPerson(ident: ${"$"}ident) {
                    $navnQueryPart,
                    $telefonQueryPart,
                    $folkeregisteridentifikatorQueryPart,
                    $statsborgerskapQueryPart,
                    $foedselQueryPart,
                    $sivilstandQueryPart,
                    $kjoennQueryPart,
                    $bostedsadresseQueryPart,
                    $deltbostedQueryPart,
                    $kontaktadresseQueryPart,
                    $oppholdsadresseQueryPart,
                }, 
                geografiskTilknytning: hentGeografiskTilknytning(ident: ${"$"}ident) {
                    $geografiskTilknytningQueryPart
                },
            }
        """.compactJson()
}

fun createPersonInfoRequest(ident: String): PersonInfoRequest {
    return PersonInfoRequest(QueryVariables(ident))
}