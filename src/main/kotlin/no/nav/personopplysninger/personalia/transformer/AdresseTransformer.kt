package no.nav.personopplysninger.personalia.transformer

import no.nav.pdl.generated.dto.hentpersonquery.PostadresseIFrittFormat
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Matrikkeladresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.PostAdresseIFrittFormat
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Postboksadresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Ukjentbosted
import no.nav.personopplysninger.personalia.dto.outbound.adresse.UtenlandskAdresse
import no.nav.personopplysninger.personalia.dto.outbound.adresse.UtenlandskAdresseIFrittFormat
import no.nav.personopplysninger.personalia.dto.outbound.adresse.Vegadresse
import no.nav.pdl.generated.dto.hentpersonquery.Matrikkeladresse as PdlMatrikkeladresse
import no.nav.pdl.generated.dto.hentpersonquery.Postboksadresse as PdlPostboksadresse
import no.nav.pdl.generated.dto.hentpersonquery.UtenlandskAdresse as PdlUtenlandskAdresse
import no.nav.pdl.generated.dto.hentpersonquery.UtenlandskAdresseIFrittFormat as PdlUtenlandskAdresseIFrittFormat
import no.nav.pdl.generated.dto.hentpersonquery.Vegadresse as PdlVegadresse


object AdresseTransformer {
    private const val FANT_IKKE_ADRESSE_ERROR_MESSAGE = "Fant ikke adresse som skulle transformeres"

    fun transformVegadresse(inbound: PdlVegadresse?, poststed: String?, kommune: String?): Vegadresse {
        inbound ?: throw IllegalArgumentException(FANT_IKKE_ADRESSE_ERROR_MESSAGE)
        return Vegadresse(
            husnummer = inbound.husnummer,
            husbokstav = inbound.husbokstav,
            bruksenhetsnummer = inbound.bruksenhetsnummer,
            adressenavn = inbound.adressenavn,
            kommune = kommune,
            tilleggsnavn = inbound.tilleggsnavn,
            postnummer = inbound.postnummer,
            poststed = poststed,
        )
    }

    fun transformPostadresseIFrittFormat(
        inbound: PostadresseIFrittFormat?,
        poststed: String?
    ): PostAdresseIFrittFormat {
        inbound ?: throw IllegalArgumentException(FANT_IKKE_ADRESSE_ERROR_MESSAGE)
        return PostAdresseIFrittFormat(
            adresselinje1 = inbound.adresselinje1,
            adresselinje2 = inbound.adresselinje2,
            adresselinje3 = inbound.adresselinje3,
            postnummer = inbound.postnummer,
            poststed = poststed,
        )
    }

    fun transformPostboksadresse(inbound: PdlPostboksadresse?, poststed: String?): Postboksadresse {
        inbound ?: throw IllegalArgumentException(FANT_IKKE_ADRESSE_ERROR_MESSAGE)
        return Postboksadresse(
            postbokseier = inbound.postbokseier,
            postboks = inbound.postboks,
            postnummer = inbound.postnummer,
            poststed = poststed,
        )
    }

    fun transformUtenlandskAdresse(
        inbound: PdlUtenlandskAdresse?,
        land: String?
    ): UtenlandskAdresse {
        inbound ?: throw IllegalArgumentException(FANT_IKKE_ADRESSE_ERROR_MESSAGE)
        return UtenlandskAdresse(
            adressenavnNummer = inbound.adressenavnNummer,
            bygningEtasjeLeilighet = inbound.bygningEtasjeLeilighet,
            postboksNummerNavn = inbound.postboksNummerNavn,
            postkode = inbound.postkode,
            bySted = inbound.bySted,
            regionDistriktOmraade = inbound.regionDistriktOmraade,
            land = land,
        )
    }

    fun transformUtenlandskAdresseIFrittFormat(
        inbound: PdlUtenlandskAdresseIFrittFormat?,
        land: String?
    ): UtenlandskAdresseIFrittFormat {
        inbound ?: throw IllegalArgumentException(FANT_IKKE_ADRESSE_ERROR_MESSAGE)
        return UtenlandskAdresseIFrittFormat(
            adresselinje1 = inbound.adresselinje1,
            adresselinje2 = inbound.adresselinje2,
            adresselinje3 = inbound.adresselinje3,
            land = land,
        )
    }

    fun transformMatrikkeladresse(
        inbound: PdlMatrikkeladresse?,
        poststed: String?,
        kommune: String?
    ): Matrikkeladresse {
        inbound ?: throw IllegalArgumentException(FANT_IKKE_ADRESSE_ERROR_MESSAGE)
        return Matrikkeladresse(
            bruksenhetsnummer = inbound.bruksenhetsnummer,
            tilleggsnavn = inbound.tilleggsnavn,
            postnummer = inbound.postnummer,
            poststed = poststed,
            kommune = kommune,
        )
    }

    fun transformUkjentBosted(
        kommune: String?
    ): Ukjentbosted {
        return Ukjentbosted(
            bostedskommune = kommune,
        )
    }
}