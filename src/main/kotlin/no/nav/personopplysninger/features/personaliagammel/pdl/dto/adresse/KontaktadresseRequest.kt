package no.nav.personopplysninger.features.personaliagammel.pdl.dto.adresse

import no.nav.personopplysninger.features.personaliagammel.pdl.PDLRequest
import no.nav.personopplysninger.features.personaliagammel.pdl.compactJson
import no.nav.personopplysninger.features.personaliagammel.pdl.dto.QueryVariables

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

const val kontaktadresseQueryPart = """ 
    kontaktadresse {
      gyldigFraOgMed
      gyldigTilOgMed
      type
      coAdressenavn
      postboksadresse {
        postbokseier
        postboks
        postnummer
      }
      vegadresse {
        matrikkelId
        husnummer
        husbokstav
        bruksenhetsnummer
        adressenavn
        kommunenummer
        tilleggsnavn
        postnummer
        koordinater {
          x
          y
          z
          kvalitet
        }
      }
      postadresseIFrittFormat {
        adresselinje1
        adresselinje2
        adresselinje3
        postnummer
      }
      utenlandskAdresse {
        adressenavnNummer
        bygningEtasjeLeilighet
        postboksNummerNavn
        postkode
        bySted
        regionDistriktOmraade
        landkode
      }
      utenlandskAdresseIFrittFormat {
        adresselinje1
        adresselinje2
        adresselinje3
        postkode
        byEllerStedsnavn
        landkode
      }
      folkeregistermetadata {
        ajourholdstidspunkt
        gyldighetstidspunkt
        opphoerstidspunkt
        kilde
        aarsak
        sekvens
      }
      metadata {
        opplysningsId
        master
        endringer {
          type
          registrert
          registrertAv
          systemkilde
          kilde
        }
        historisk
      }
    }
"""
