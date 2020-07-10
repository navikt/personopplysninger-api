package no.nav.personopplysninger.features.endreopplysninger

import no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse.*
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.EndringKontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.Kontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.opphoer.EndringOpphoerPersonopplysning
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.EndringTelefon
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.Telefonnummer
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.endreNummerPayload
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.slettNummerPayload
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.DownstreamPostboksadresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.DownstreamUtenlandskAdresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.DownstreamVegadresse
import no.nav.personopplysninger.features.personalia.pdl.PdlService
import no.nav.personopplysninger.oppslag.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.oppslag.kodeverk.api.KodeOgTekstDto
import no.nav.personopplysninger.oppslag.kodeverk.api.Kodeverk
import no.nav.personopplysninger.oppslag.kodeverk.api.RetningsnummerDTO
import no.nav.personopplysninger.oppslag.sts.STSConsumer
import org.springframework.stereotype.Service

@Service
class EndreOpplysningerService (
        private var stsConsumer: STSConsumer,
        private var personMottakConsumer: PersonMottakConsumer,
        private var kodeverkConsumer: KodeverkConsumer,
        private var pdlService: PdlService
) {

    fun endreTelefonnummer(fnr: String, telefonnummer: Telefonnummer): EndringTelefon {
        if (!setOf(1, 2).contains(telefonnummer.prioritet)) {
            throw RuntimeException("Støtter kun prioritet [1, 2] eller type ['HJEM', 'MOBIL']")
        } else {
            return personMottakConsumer.endreTelefonnummer(fnr, endreNummerPayload(fnr, telefonnummer), systemToken)
        }
    }

    fun slettTelefonNummer(fnr: String, telefonnummer: Telefonnummer): EndringOpphoerPersonopplysning {
        val opplysningsId = pdlService.getOpplysningsIdForTelefon(fnr, telefonnummer.landskode!!, telefonnummer.nummer!!)
                ?: throw RuntimeException("Kan ikke slette nummer som ikke eksisterer: ${telefonnummer.landskode}, ${telefonnummer.nummer}")

        return personMottakConsumer.slettPersonopplysning(fnr, slettNummerPayload(fnr, opplysningsId), systemToken)
    }

    fun endreKontonummer(fnr: String, kontonummer: Kontonummer): EndringKontonummer {
        return personMottakConsumer.endreKontonummer(fnr, kontonummer, systemToken)
    }

    fun endreKontaktadresseVegadresse(fnr: String, vegadresse: DownstreamVegadresse): EndringKontaktadresse {
        return validateVegadresse(vegadresse).ifValid {
            val upstreamVegadresse = PMKontaktAdresseTransformer.fromDownstreamVegadresse(vegadresse)
            personMottakConsumer.endreKontaktadresse(fnr, endreKontaktadressePayload(fnr, upstreamVegadresse), systemToken)
        }.ifInvalid { error ->
            EndringKontaktadresse.validationError(error)
        }.response
    }

    fun endreKontaktadressePostboksadresse(fnr: String, postboksadresse: DownstreamPostboksadresse): EndringKontaktadresse {
        return validatePostboksAdresse(postboksadresse).ifValid {
            val upstreamVegadresse = PMKontaktAdresseTransformer.fromDownstreamPostboksadresse(postboksadresse)
            personMottakConsumer.endreKontaktadresse(fnr, endreKontaktadressePayload(fnr, upstreamVegadresse), systemToken)
        }.ifInvalid { error ->
            EndringKontaktadresse.validationError(error)
        }.response
    }

    fun endreKontaktadresseUtenlandskAdresse(fnr: String, utenlandskAdresse: DownstreamUtenlandskAdresse): EndringKontaktadresse {
        return validateUtenlandskAdresse(utenlandskAdresse).ifValid {
            val upstreamVegadresse = PMKontaktAdresseTransformer.fromDownstreamutenlandskAdresse(utenlandskAdresse)
            personMottakConsumer.endreKontaktadresse(fnr, endreKontaktadressePayload(fnr, upstreamVegadresse), systemToken)
        }.ifInvalid { error ->
            EndringKontaktadresse.validationError(error)
        }.response
    }

    fun slettKontaktadresse(fnr: String): EndringOpphoerPersonopplysning {
        val opplysningsId = pdlService.getOpplysningsIdForKontaktadresse(fnr)
                ?: throw RuntimeException("Fant ingen kontaktadresser som kan slettes")

        return personMottakConsumer.slettPersonopplysning(fnr, slettKontaktadressePayload(fnr, opplysningsId), systemToken)
    }

    fun hentRetningsnumre(): Array<RetningsnummerDTO> {
        return kodeverkConsumer.hentRetningsnumre().koder
                .map { kode -> RetningsnummerDTO(kode.navn, kode.betydninger.first().beskrivelser.entries.first().value.tekst) }
                .sortedBy { it.land }
                .toTypedArray()
    }

    fun hentLand(): Array<KodeOgTekstDto> {
        return toSortedKodeOgTekstArray(kodeverkConsumer.hentLandKoder())
    }

    fun hentValuta(): Array<KodeOgTekstDto> {
        return toSortedKodeOgTekstArray(kodeverkConsumer.hentValuta())
    }

    fun hentPostnummer(): Array<KodeOgTekstDto> {
        return toSortedKodeOgTekstArray(kodeverkConsumer.hentPostnummer())
    }

    private fun toSortedKodeOgTekstArray(kodeverk: Kodeverk): Array<KodeOgTekstDto> {
        return kodeverk.koder
                .filter { kode -> kode.betydninger.isNotEmpty() }
                .map { kode -> KodeOgTekstDto.fromKode(kode)}
                .sortedBy { it.tekst }
                .toTypedArray()
    }

    private inline val systemToken: String get() = stsConsumer.token.access_token
}
