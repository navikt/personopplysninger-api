package no.nav.personopplysninger.endreopplysninger

import no.nav.personopplysninger.common.consumer.kodeverk.KodeverkService
import no.nav.personopplysninger.common.consumer.kodeverk.dto.KodeOgTekstDto
import no.nav.personopplysninger.common.consumer.kodeverk.dto.Kodeverk
import no.nav.personopplysninger.common.consumer.kodeverk.dto.Retningsnummer
import no.nav.personopplysninger.common.consumer.kontoregister.KontoregisterConsumer
import no.nav.personopplysninger.common.consumer.kontoregister.dto.inbound.Kontonummer
import no.nav.personopplysninger.common.consumer.kontoregister.dto.inbound.Landkode
import no.nav.personopplysninger.common.consumer.kontoregister.dto.inbound.Valutakode
import no.nav.personopplysninger.common.consumer.kontoregister.dto.outbound.OppdaterKonto
import no.nav.personopplysninger.common.consumer.kontoregister.dto.outbound.UtenlandskKontoInfo
import no.nav.personopplysninger.common.consumer.pdl.PdlService
import no.nav.personopplysninger.endreopplysninger.consumer.PdlMottakConsumer
import no.nav.personopplysninger.endreopplysninger.dto.inbound.Telefonnummer
import no.nav.personopplysninger.endreopplysninger.dto.inbound.endreNummerPayload
import no.nav.personopplysninger.endreopplysninger.dto.inbound.slettKontaktadressePayload
import no.nav.personopplysninger.endreopplysninger.dto.inbound.slettNummerPayload
import no.nav.personopplysninger.endreopplysninger.dto.outbound.Endring

class EndreOpplysningerService(
    private var pdlMottakConsumer: PdlMottakConsumer,
    private var kodeverkService: KodeverkService,
    private var kontoregisterConsumer: KontoregisterConsumer,
    private var pdlService: PdlService
) {

    suspend fun endreTelefonnummer(token: String, fnr: String, telefonnummer: Telefonnummer): Endring {
        if (!setOf(1, 2).contains(telefonnummer.prioritet)) {
            throw RuntimeException("Støtter kun prioritet [1, 2] eller type ['HJEM', 'MOBIL']")
        } else {
            return pdlMottakConsumer.endreTelefonnummer(token, fnr, endreNummerPayload(fnr, telefonnummer))
        }
    }

    suspend fun slettTelefonNummer(token: String, fnr: String, telefonnummer: Telefonnummer): Endring {
        val opplysningsId =
            pdlService.getOpplysningsIdForTelefon(token, fnr, telefonnummer.landskode, telefonnummer.nummer)
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

    suspend fun endreKontonummer(token: String, fnr: String, kontonummer: Kontonummer) {
        val request = OppdaterKonto(
            kontohaver = fnr,
            nyttKontonummer = kontonummer.value,
            utenlandskKontoInfo = kontonummer.utenlandskKontoInformasjon?.let {
                UtenlandskKontoInfo(
                    banknavn = kontonummer.utenlandskKontoInformasjon.bank?.navn.orEmpty(),
                    bankkode = kontonummer.utenlandskKontoInformasjon.bank?.kode,
                    bankLandkode = kontonummer.utenlandskKontoInformasjon.landkode.orEmpty(),
                    valutakode = kontonummer.utenlandskKontoInformasjon.valuta,
                    swiftBicKode = kontonummer.utenlandskKontoInformasjon.swift,
                    bankadresse1 = kontonummer.utenlandskKontoInformasjon.bank?.adresseLinje1.orEmpty(),
                    bankadresse2 = kontonummer.utenlandskKontoInformasjon.bank?.adresseLinje2.orEmpty(),
                    bankadresse3 = kontonummer.utenlandskKontoInformasjon.bank?.adresseLinje3.orEmpty(),
                )
            }
        )
        kontoregisterConsumer.endreKontonummer(token, request)
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

    suspend fun hentLandkoder(): List<Landkode> {
        return kontoregisterConsumer.hentLandkoder()
    }

    suspend fun hentValutakoder(): List<Valutakode> {
        return kontoregisterConsumer.hentValutakoder()
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
