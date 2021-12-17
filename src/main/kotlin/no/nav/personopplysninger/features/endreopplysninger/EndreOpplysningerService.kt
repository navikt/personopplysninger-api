package no.nav.personopplysninger.features.endreopplysninger

import no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse.EndringKontaktadresse
import no.nav.personopplysninger.features.endreopplysninger.domain.kontaktadresse.slettKontaktadressePayload
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.EndringKontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.Kontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.EndringTelefon
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.Telefonnummer
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.endreNummerPayload
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.slettNummerPayload
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

    fun slettTelefonNummer(fnr: String, telefonnummer: Telefonnummer): EndringTelefon {
        val opplysningsId = pdlService.getOpplysningsIdForTelefon(fnr, telefonnummer.landskode!!, telefonnummer.nummer!!)
                ?: throw RuntimeException("Kan ikke slette nummer som ikke eksisterer: ${telefonnummer.landskode}, ${telefonnummer.nummer}")

        return personMottakConsumer.slettPersonopplysning(fnr, slettNummerPayload(fnr, opplysningsId), systemToken, EndringTelefon::class.java)
    }

    fun endreKontonummer(fnr: String, kontonummer: Kontonummer): EndringKontonummer {
        return personMottakConsumer.endreKontonummer(fnr, kontonummer, systemToken)
    }

    fun slettKontaktadresse(fnr: String): EndringKontaktadresse {
        val opplysningsId = pdlService.getOpplysningsIdForKontaktadresse(fnr)
                ?: throw RuntimeException("Fant ingen kontaktadresser som kan slettes")

        return personMottakConsumer.slettPersonopplysning(fnr, slettKontaktadressePayload(fnr, opplysningsId), systemToken, EndringKontaktadresse::class.java)
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
