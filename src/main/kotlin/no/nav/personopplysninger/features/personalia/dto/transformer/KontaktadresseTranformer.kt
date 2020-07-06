package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.*
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.PdlKontaktadresse
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.util.PdlKontaktadresseMappingType.*
import no.nav.personopplysninger.features.personalia.pdl.dto.adresse.util.mappingType

object KontaktadresseTranformer {
    fun toOutbound(inbound: PdlKontaktadresse, kodeverk: PersonaliaKodeverk): Kontaktadresse {
        return when (inbound.mappingType) {
            INNLAND_VEGADRESSE -> transformFromVegadresse(inbound, kodeverk)
            INNLAND_FRIFORMADRESSE -> transformFromPostadresseIFrittFormat(inbound, kodeverk)
            INNLAND_POSTBOKSADRESSE -> transformFromPostboksadresse(inbound, kodeverk)
            UTLAND_ADRESSE -> transformFromUtenlandskAdresse(inbound, kodeverk)
            UTLAND_FRIFORMADRESSE -> transformFromUtenlandskAdresseIFrittFormat(inbound, kodeverk)
            EMPTY -> throw IllegalStateException("Prøvde å transformere tomt Pdl-Kontaktadresse objekt.")
        }
    }

    private fun transformFromVegadresse(inbound: PdlKontaktadresse, kodeverk: PersonaliaKodeverk): Kontaktadresse {
        val inboundVegadresse = inbound.vegadresse!!

        val vegadresse = Vegadresse(
                husnummer = inboundVegadresse.husnummer,
                husbokstav = inboundVegadresse.husbokstav,
                bruksenhetsnummer = inboundVegadresse.bruksenhetsnummer,
                adressenavn = inboundVegadresse.adressenavn,
                kommunenummer = inboundVegadresse.kommunenummer,
                tilleggsnavn = inboundVegadresse.tilleggsnavn,
                postnummer = inboundVegadresse.postnummer,
                poststed = kodeverk.kontaktadressePostSted,
                gyldigFraOgMed = inbound.gyldigFraOgMed.toString(),
                gyldigTilOgMed = inbound.gyldigTilOgMed.toString(),
                coAdressenavn = inbound.coAdressenavn
        )

        return Kontaktadresse.withVegadresse(vegadresse)
    }

    private fun transformFromPostadresseIFrittFormat(inbound: PdlKontaktadresse, kodeverk: PersonaliaKodeverk): Kontaktadresse {
        val inboundPostadresseIFrittFormat = inbound.postadresseIFrittFormat!!

        val postadresseIFrittFormat = PostAdresseIFrittFormat(
                adresselinje1 = inboundPostadresseIFrittFormat.adresselinje1,
                adresselinje2 = inboundPostadresseIFrittFormat.adresselinje2,
                adresselinje3 = inboundPostadresseIFrittFormat.adresselinje3,
                postnummer = inboundPostadresseIFrittFormat.postnummer,
                poststed = kodeverk.kontaktadressePostSted,
                gyldigFraOgMed = inbound.gyldigFraOgMed.toString(),
                gyldigTilOgMed = inbound.gyldigTilOgMed.toString(),
                coAdressenavn = inbound.coAdressenavn
        )

        return Kontaktadresse.withPostadresseIFrittFormat(postadresseIFrittFormat)
    }

    private fun transformFromPostboksadresse(inbound: PdlKontaktadresse, kodeverk: PersonaliaKodeverk): Kontaktadresse {
        val inboundPostboksadresse = inbound.postboksadresse!!

        val postboksadresse = Postboksadresse(
                postbokseier = inboundPostboksadresse.postbokseier,
                postboks = inboundPostboksadresse.postboks,
                postnummer = inboundPostboksadresse.postnummer,
                poststed = kodeverk.kontaktadressePostSted,
                gyldigFraOgMed = inbound.gyldigFraOgMed.toString(),
                gyldigTilOgMed = inbound.gyldigTilOgMed.toString()
        )

        return Kontaktadresse.withPostboksadresse(postboksadresse)
    }

    private fun transformFromUtenlandskAdresse(inbound: PdlKontaktadresse, kodeverk: PersonaliaKodeverk): Kontaktadresse {
        val inboundUtenlandskAdresse = inbound.utenlandskAdresse!!

        val utenlandskAdresse = UtenlandskAdresse(
                adressenavnNummer = inboundUtenlandskAdresse.adressenavnNummer,
                bygningEtasjeLeilighet = inboundUtenlandskAdresse.bygningEtasjeLeilighet,
                postboksNummerNavn = inboundUtenlandskAdresse.postboksNummerNavn,
                postkode = inboundUtenlandskAdresse.postkode,
                bySted = inboundUtenlandskAdresse.bySted,
                regionDistriktOmraade = inboundUtenlandskAdresse.regionDistriktOmraade,
                landkode = inboundUtenlandskAdresse.landkode,
                land = kodeverk.kontaktadresseLand,
                gyldigFraOgMed = inbound.gyldigFraOgMed.toString(),
                gyldigTilOgMed = inbound.gyldigTilOgMed.toString(),
                coAdressenavn = inbound.coAdressenavn
        )

        return Kontaktadresse.withUtenlandskAdresse(utenlandskAdresse)
    }

    private fun transformFromUtenlandskAdresseIFrittFormat(inbound: PdlKontaktadresse, kodeverk: PersonaliaKodeverk): Kontaktadresse {
        val inboundUtenlandskAdresseIFrittFormat = inbound.utenlandskAdresseIFrittFormat!!

        val utenlandskAdressseIFrittFormat = UtenlandskAdresseIFrittFormat(
                adresselinje1 = inboundUtenlandskAdresseIFrittFormat.adresselinje1,
                adresselinje2 = inboundUtenlandskAdresseIFrittFormat.adresselinje2,
                adresselinje3 = inboundUtenlandskAdresseIFrittFormat.adresselinje3,
                postkode = inboundUtenlandskAdresseIFrittFormat.postkode,
                byEllerStedsnavn = inboundUtenlandskAdresseIFrittFormat.byEllerStedsnavn,
                landkode = inboundUtenlandskAdresseIFrittFormat.landkode,
                land = kodeverk.kontaktadresseLand,
                gyldigFraOgMed = inbound.gyldigFraOgMed.toString(),
                gyldigTilOgMed = inbound.gyldigTilOgMed.toString(),
                coAdressenavn = inbound.coAdressenavn
        )

        return Kontaktadresse.withUtenlandskAdresseIFrittFormat(utenlandskAdressseIFrittFormat)
    }
 }