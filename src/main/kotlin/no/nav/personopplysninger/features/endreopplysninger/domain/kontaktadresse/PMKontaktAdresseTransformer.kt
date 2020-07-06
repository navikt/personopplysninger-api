package no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.DownstreamPostboksadresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.DownstreamUtenlandskAdresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.DownstreamVegadresse
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlKontaktadresse
import java.time.LocalDate

object PMKontaktAdresseTransformer {
    fun fromDownstreamVegadresse(vegadresse: DownstreamVegadresse): Kontaktadresse {
        val adresse = Vegadresse(
                matrikkelId = "",
                bruksenhetsnummer = vegadresse.bruksenhetsnummer,
                adressenavn = vegadresse.adressenavn,
                husnummer = vegadresse.husnummer,
                husbokstav = vegadresse.husbokstav,
                tilleggsnavn = vegadresse.tilleggsnavn,
                postnummer = vegadresse.postnummer
        )

        return Kontaktadresse(
                gyldigFraOgMed = LocalDate.parse(vegadresse.gyldigFraOgMed),
                gyldigTilOgMed = LocalDate.parse(vegadresse.gyldigTilOgMed),
                coAdressenavn = vegadresse.coAdressenavn,
                adresse = adresse
        )
    }

    fun fromDownstreamPostboksadresse(postboksadresse: DownstreamPostboksadresse): Kontaktadresse {
        val adresse = Postboksadresse(
                postbokseier = postboksadresse.postbokseier,
                postboks = postboksadresse.postboks,
                postnummer = postboksadresse.postnummer!!
        )

        return Kontaktadresse(
                gyldigFraOgMed = LocalDate.parse(postboksadresse.gyldigFraOgMed),
                gyldigTilOgMed = LocalDate.parse(postboksadresse.gyldigTilOgMed),
                coAdressenavn = "",
                adresse = adresse
        )
    }

    fun fromDownstreamutenlandskAdresse(utenlandskAdresse: DownstreamUtenlandskAdresse): Kontaktadresse {
        val adresse = UtenlandskAdresse(
                adressenavnNummer = utenlandskAdresse.adressenavnNummer,
                bygningEtasjeLeilighet = utenlandskAdresse.bygningEtasjeLeilighet,
                postboksNummerNavn = utenlandskAdresse.postboksNummerNavn,
                postkode = utenlandskAdresse.postkode,
                bySted = utenlandskAdresse.bySted,
                regionDistriktOmraade = utenlandskAdresse.regionDistriktOmraade,
                landkode = utenlandskAdresse.landkode
        )

        return Kontaktadresse(
                gyldigFraOgMed = LocalDate.parse(utenlandskAdresse.gyldigFraOgMed),
                gyldigTilOgMed = LocalDate.parse(utenlandskAdresse.gyldigTilOgMed),
                coAdressenavn = utenlandskAdresse.coAdressenavn,
                adresse = adresse
        )
    }
}