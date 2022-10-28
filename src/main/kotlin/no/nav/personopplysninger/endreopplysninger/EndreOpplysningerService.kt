package no.nav.personopplysninger.endreopplysninger

import no.nav.personopplysninger.common.kodeverk.KodeverkService
import no.nav.personopplysninger.common.kodeverk.dto.KodeOgTekstDto
import no.nav.personopplysninger.common.kodeverk.dto.Kodeverk
import no.nav.personopplysninger.common.pdl.PdlService
import no.nav.personopplysninger.endreopplysninger.consumer.PdlMottakConsumer
import no.nav.personopplysninger.endreopplysninger.dto.inbound.Kontonummer
import no.nav.personopplysninger.endreopplysninger.dto.inbound.Telefonnummer
import no.nav.personopplysninger.endreopplysninger.dto.inbound.endreNummerPayload
import no.nav.personopplysninger.endreopplysninger.dto.inbound.slettKontaktadressePayload
import no.nav.personopplysninger.endreopplysninger.dto.inbound.slettNummerPayload
import no.nav.personopplysninger.endreopplysninger.dto.outbound.Endring
import no.nav.personopplysninger.endreopplysninger.dto.outbound.Retningsnummer

class EndreOpplysningerService(
    private var pdlMottakConsumer: PdlMottakConsumer,
    private var kodeverkService: KodeverkService,
    private var pdlService: PdlService
) {

    suspend fun endreTelefonnummer(token: String, fnr: String, telefonnummer: Telefonnummer): Endring {
        if (!setOf(1, 2).contains(telefonnummer.prioritet)) {
            throw RuntimeException("Støtter kun prioritet [1, 2] eller type ['HJEM', 'MOBIL']")
        } else {
            return pdlMottakConsumer.endreTelefonnummer(token, fnr, endreNummerPayload(fnr, telefonnummer))
        }
    }

    suspend fun endreKontonummer(token: String, fnr: String, kontonummer: Kontonummer): Endring {
        return pdlMottakConsumer.endreKontonummer(token, fnr, kontonummer)
    }

    suspend fun slettTelefonNummer(token: String, fnr: String, telefonnummer: Telefonnummer): Endring {
        val opplysningsId =
            pdlService.getOpplysningsIdForTelefon(token, fnr, telefonnummer.landskode!!, telefonnummer.nummer!!)
                ?: throw RuntimeException("Fant ikke oppgitt telefonnummer")

        return pdlMottakConsumer.slettPersonopplysning(
            token,
            fnr,
            slettNummerPayload(fnr, opplysningsId)
        )
    }

    suspend fun slettKontaktadresse(token: String, fnr: String): Endring {
        val opplysningsId = pdlService.getOpplysningsIdForKontaktadresse(token, fnr)
            ?: throw RuntimeException("Fant ingen kontaktadresser som kan slettes")

        return pdlMottakConsumer.slettPersonopplysning(
            token,
            fnr,
            slettKontaktadressePayload(fnr, opplysningsId),
        )
    }

    suspend fun hentRetningsnumre(): Array<Retningsnummer> {
        return kodeverkService.hentRetningsnumre().koder
            .map { kode ->
                Retningsnummer(
                    kode.navn,
                    kode.betydninger.first().beskrivelser.entries.first().value.tekst
                )
            }
            .sortedBy { it.land }
            .toTypedArray()
    }

    suspend fun hentLand(): Array<KodeOgTekstDto> {
        return toSortedKodeOgTekstArray(kodeverkService.hentLandKoder())
    }

    suspend fun hentValuta(): Array<KodeOgTekstDto> {
        return toSortedKodeOgTekstArray(kodeverkService.hentValuta())
    }

    suspend fun hentPostnummer(): Array<KodeOgTekstDto> {
        return toSortedKodeOgTekstArray(kodeverkService.hentPostnummer())
    }

    private fun toSortedKodeOgTekstArray(kodeverk: Kodeverk): Array<KodeOgTekstDto> {
        return kodeverk.koder
            .filter { kode -> kode.betydninger.isNotEmpty() }
            .map { kode -> KodeOgTekstDto.fromKode(kode) }
            .sortedBy { it.tekst }
            .toTypedArray()
    }
}
