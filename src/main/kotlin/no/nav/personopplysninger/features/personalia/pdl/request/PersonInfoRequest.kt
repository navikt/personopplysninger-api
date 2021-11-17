package no.nav.personopplysninger.features.personalia.pdl.request

class PersonInfoRequest(override val variables: QueryVariables) : PDLRequest {

    override val query: String
        get() = """
            query (${"$"}ident: ID!) {
                person: hentPerson(ident: ${"$"}ident) {
                    $navnQueryPart,
                    $telefonQueryPart,
                    $folkeregisterpersonstatusQueryPart,
                    $statsborgerskapQueryPart,
                    $foedselQueryPart,
                    $sivilstandQueryPart,
                    $kjoennQueryPart,
                    $bostedsadresseQueryPart,
                    $deltbostedQueryPart,
                    $kontaktadresseQueryPart,
                    $oppholdsadresseQueryPart,
                }, 
                geografiskTilknytning: hentGeografiskTilknytning(ident: "10108000398") {
                    $geografiskTilknytningQueryPart
                },
                identer: hentIdenter(ident: "10108000398", grupper: [AKTORID, FOLKEREGISTERIDENT, NPID]) {
                    $identerQueryPart
                },
            }
        """.compactJson()
}

fun createPersonInfoRequest(ident: String): PersonInfoRequest {
    return PersonInfoRequest(QueryVariables(ident))
}