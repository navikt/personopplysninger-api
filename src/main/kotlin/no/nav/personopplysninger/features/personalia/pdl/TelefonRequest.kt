package no.nav.personopplysninger.features.personalia.pdl

import no.nav.personopplysninger.features.personalia.pdl.dto.QueryVariables

class TelefonRequest(override val variables: QueryVariables) : PDLRequest {

    override val query: String
        get() = """
            query (${"$"}ident: ID!) {
                hentPerson(ident: ${"$"}ident) {
                    telefonnummer { landskode, nummer, prioritet, metadata { opplysningsId } }
                } 
            }
        """.compactJson()
}

fun createTelefonRequest(ident: String): TelefonRequest {
    return TelefonRequest(QueryVariables(ident))
}