package no.nav.personopplysninger.features.personaliagammel.pdl.dto.telefon

import no.nav.personopplysninger.features.personaliagammel.pdl.PDLRequest
import no.nav.personopplysninger.features.personaliagammel.pdl.compactJson
import no.nav.personopplysninger.features.personaliagammel.pdl.dto.QueryVariables

class TelefonRequest(override val variables: QueryVariables) : PDLRequest {

    override val query: String
        get() = """
            query (${"$"}ident: ID!) {
                person: hentPerson(ident: ${"$"}ident) {
                    $telefonQueryPart
                } 
            }
        """.compactJson()
}

const val telefonQueryPart = "telefonnummer { landskode, nummer, prioritet, metadata { opplysningsId } }"

fun createTelefonRequest(ident: String): TelefonRequest {
    return TelefonRequest(QueryVariables(ident))
}