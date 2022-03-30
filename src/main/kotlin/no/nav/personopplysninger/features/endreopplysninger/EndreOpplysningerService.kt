package no.nav.personopplysninger.features.endreopplysninger

import no.nav.personopplysninger.consumer.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.consumer.kodeverk.domain.KodeOgTekstDto
import no.nav.personopplysninger.consumer.kodeverk.domain.Kodeverk
import no.nav.personopplysninger.consumer.kodeverk.domain.RetningsnummerDTO
import no.nav.personopplysninger.consumer.pdl.PdlService
import no.nav.personopplysninger.consumer.personmottak.PersonMottakConsumer
import no.nav.personopplysninger.consumer.personmottak.domain.kontaktadresse.EndringKontaktadresse
import no.nav.personopplysninger.consumer.personmottak.domain.kontaktadresse.slettKontaktadressePayload
import no.nav.personopplysninger.consumer.personmottak.domain.kontonummer.EndringKontonummer
import no.nav.personopplysninger.consumer.personmottak.domain.kontonummer.Kontonummer
import no.nav.personopplysninger.consumer.personmottak.domain.telefon.EndringTelefon
import no.nav.personopplysninger.consumer.personmottak.domain.telefon.Telefonnummer
import no.nav.personopplysninger.consumer.personmottak.domain.telefon.endreNummerPayload
import no.nav.personopplysninger.consumer.personmottak.domain.telefon.slettNummerPayload
import org.springframework.stereotype.Service

@Service
class EndreOpplysningerService (
    private var personMottakConsumer: PersonMottakConsumer,
    private var kodeverkConsumer: KodeverkConsumer,
    private var pdlService: PdlService
) {

    fun endreTelefonnummer(fnr: String, telefonnummer: Telefonnummer): EndringTelefon {
        if (!setOf(1, 2).contains(telefonnummer.prioritet)) {
            throw RuntimeException("Støtter kun prioritet [1, 2] eller type ['HJEM', 'MOBIL']")
        } else {
            return personMottakConsumer.endreTelefonnummer(fnr, endreNummerPayload(fnr, telefonnummer))
        }
    }

    fun slettTelefonNummer(fnr: String, telefonnummer: Telefonnummer): EndringTelefon {
        val opplysningsId = pdlService.getOpplysningsIdForTelefon(fnr, telefonnummer.landskode!!, telefonnummer.nummer!!)
                ?: throw RuntimeException("Kan ikke slette nummer som ikke eksisterer: ${telefonnummer.landskode}, ${telefonnummer.nummer}")

        return personMottakConsumer.slettPersonopplysning(fnr, slettNummerPayload(fnr, opplysningsId), EndringTelefon::class.java)
    }

    fun endreKontonummer(fnr: String, kontonummer: Kontonummer): EndringKontonummer {
        return personMottakConsumer.endreKontonummer(fnr, kontonummer)
    }

    fun slettKontaktadresse(fnr: String): EndringKontaktadresse {
        val opplysningsId = pdlService.getOpplysningsIdForKontaktadresse(fnr)
                ?: throw RuntimeException("Fant ingen kontaktadresser som kan slettes")

        return personMottakConsumer.slettPersonopplysning(fnr, slettKontaktadressePayload(fnr, opplysningsId), EndringKontaktadresse::class.java)
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
}
