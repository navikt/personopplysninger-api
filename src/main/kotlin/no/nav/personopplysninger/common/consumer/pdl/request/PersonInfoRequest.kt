package no.nav.personopplysninger.common.consumer.pdl.request

import kotlinx.serialization.Serializable

@Serializable
class PersonInfoRequest(override val variables: QueryVariables) : PDLRequest {

    override val query: String = """
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