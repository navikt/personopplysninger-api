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
                gyldigFraOgMed = vegadresse.gyldigFraOgMed!!,
                gyldigTilOgMed = vegadresse.gyldigTilOgMed!!,
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
                gyldigFraOgMed = postboksadresse.gyldigFraOgMed!!,
                gyldigTilOgMed = postboksadresse.gyldigTilOgMed!!,
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
                gyldigFraOgMed = utenlandskAdresse.gyldigFraOgMed!!,
                gyldigTilOgMed = utenlandskAdresse.gyldigTilOgMed!!,
                coAdressenavn = utenlandskAdresse.coAdressenavn,
                adresse = adresse
        )
    }
}