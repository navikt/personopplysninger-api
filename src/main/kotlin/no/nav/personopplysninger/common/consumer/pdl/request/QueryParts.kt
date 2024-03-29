package no.nav.personopplysninger.common.consumer.pdl.request


const val navnQueryPart = "navn {fornavn, mellomnavn, etternavn}"
const val telefonQueryPart = "telefonnummer { landskode, nummer, prioritet, metadata { opplysningsId } }"
const val folkeregisteridentifikatorQueryPart = "folkeregisteridentifikator{identifikasjonsnummer, status, type}"
const val statsborgerskapQueryPart = "statsborgerskap{land, gyldigTilOgMed}"
const val foedselQueryPart = "foedsel{foedested, foedekommune, foedeland}"
const val sivilstandQueryPart = "sivilstand{type, gyldigFraOgMed}"
const val kjoennQueryPart = "kjoenn{kjoenn}"
const val bostedsadresseQueryPart = """
    bostedsadresse {
      angittFlyttedato,
      gyldigFraOgMed,
      gyldigTilOgMed,
      coAdressenavn,
      vegadresse {husnummer, husbokstav, bruksenhetsnummer, adressenavn, kommunenummer, bydelsnummer, tilleggsnavn, postnummer},
      matrikkeladresse{bruksenhetsnummer, tilleggsnavn, postnummer, kommunenummer},
      utenlandskAdresse{adressenavnNummer, bygningEtasjeLeilighet, postboksNummerNavn, postkode, bySted, regionDistriktOmraade, landkode},
      ukjentBosted{bostedskommune}
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
const val deltbostedQueryPart = """
    deltBosted {
      startdatoForKontrakt,
      sluttdatoForKontrakt,
      coAdressenavn,
      vegadresse {husnummer, husbokstav, bruksenhetsnummer, adressenavn, kommunenummer, bydelsnummer, tilleggsnavn, postnummer},
      matrikkeladresse{bruksenhetsnummer, tilleggsnavn, postnummer, kommunenummer},
      utenlandskAdresse{adressenavnNummer, bygningEtasjeLeilighet, postboksNummerNavn, postkode, bySted, regionDistriktOmraade, landkode},
      ukjentBosted{bostedskommune}
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
const val oppholdsadresseQueryPart = """
    oppholdsadresse {
      gyldigFraOgMed,
      gyldigTilOgMed,
      coAdressenavn,
      utenlandskAdresse{adressenavnNummer, bygningEtasjeLeilighet, postboksNummerNavn, postkode, bySted, regionDistriktOmraade, landkode},
      vegadresse {husnummer, husbokstav, bruksenhetsnummer, adressenavn, kommunenummer, bydelsnummer, tilleggsnavn, postnummer},
      matrikkeladresse{bruksenhetsnummer, tilleggsnavn, postnummer, kommunenummer},
      oppholdAnnetSted
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
const val geografiskTilknytningQueryPart = "gtKommune, gtBydel, gtLand"