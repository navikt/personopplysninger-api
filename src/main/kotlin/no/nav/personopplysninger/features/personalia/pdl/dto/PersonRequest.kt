package no.nav.personopplysninger.features.personalia.pdl.dto

import no.nav.personopplysninger.features.personalia.pdl.PDLRequest
import no.nav.personopplysninger.features.personalia.pdl.compactJson
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.kontaktadresseQueryPart
import no.nav.personopplysninger.features.personalia.pdl.dto.telefon.telefonQueryPart

class PersonRequest(override val variables: QueryVariables): PDLRequest {

    override val query: String
        get() = """
            query (${"$"}ident: ID!) {
                person: hentPerson(ident: ${"$"}ident) {
                    $telefonQueryPart,
                    $kontaktadresseQueryPart
                } 
            }
        """.compactJson()
}

fun createPersonInfoRequest(ident: String): PersonRequest {
    return PersonRequest(QueryVariables(ident))
}