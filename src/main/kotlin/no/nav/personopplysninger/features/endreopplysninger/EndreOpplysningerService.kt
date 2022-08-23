package no.nav.personopplysninger.features.endreopplysninger

import no.nav.personopplysninger.consumer.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.consumer.kodeverk.dto.KodeOgTekstDto
import no.nav.personopplysninger.consumer.kodeverk.dto.Kodeverk
import no.nav.personopplysninger.consumer.kontoregister.KontoregisterConsumer
import no.nav.personopplysninger.consumer.kontoregister.dto.OppdaterKonto
import no.nav.personopplysninger.consumer.kontoregister.dto.UtenlandskKontoInfo
import no.nav.personopplysninger.consumer.pdl.PdlService
import no.nav.personopplysninger.consumer.pdlmottak.PdlMottakConsumer
import no.nav.personopplysninger.consumer.pdlmottak.dto.Endring
import no.nav.personopplysninger.consumer.pdlmottak.dto.Telefonnummer
import no.nav.personopplysninger.consumer.pdlmottak.dto.endreNummerPayload
import no.nav.personopplysninger.consumer.pdlmottak.dto.slettKontaktadressePayload
import no.nav.personopplysninger.consumer.pdlmottak.dto.slettNummerPayload
import no.nav.personopplysninger.features.endreopplysninger.dto.Kontonummer
import no.nav.personopplysninger.features.endreopplysninger.dto.Retningsnummer

class EndreOpplysningerService(
    private var pdlMottakConsumer: PdlMottakConsumer,
    private var kodeverkConsumer: KodeverkConsumer,
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
            pdlService.getOpplysningsIdForTelefon(token, fnr, telefonnummer.landskode!!, telefonnummer.nummer!!)
                ?: throw RuntimeException("Kan ikke slette nummer som ikke eksisterer: ${telefonnummer.landskode}, ${telefonnummer.nummer}")

        return pdlMottakConsumer.slettPersonopplysning(
            token,
            fnr,
            slettNummerPayload(fnr, opplysningsId)
        )
    }

    suspend fun endreKontonummer(token: String, fnr: String, kontonummer: Kontonummer) {
        val request = OppdaterKonto(
            kontohaver = fnr,
            nyttKontonummer = kontonummer.value,
            utenlandskKontoInfo = kontonummer.utenlandskKontoInformasjon?.let {
                UtenlandskKontoInfo(
                    banknavn = kontonummer.utenlandskKontoInformasjon.bank?.navn.orEmpty(),
                    bankkode = kontonummer.utenlandskKontoInformasjon.bank?.kode.orEmpty(),
                    bankLandkode = kontonummer.utenlandskKontoInformasjon.landkodeTobokstavs.orEmpty(),
                    valutakode = kontonummer.utenlandskKontoInformasjon.valuta!!,
                    swiftBicKode = kontonummer.utenlandskKontoInformasjon.swift.orEmpty(),
                    bankadresse1 = kontonummer.utenlandskKontoInformasjon.bank?.adresseLinje1.orEmpty(),
                    bankadresse2 = kontonummer.utenlandskKontoInformasjon.bank?.adresseLinje2.orEmpty(),
                    bankadresse3 = kontonummer.utenlandskKontoInformasjon.bank?.adresseLinje3.orEmpty(),
                )
            }
        )
        kontoregisterConsumer.endreKontonummer(token, request)
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
        return kodeverkConsumer.hentRetningsnumre().koder
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
        return toSortedKodeOgTekstArray(kodeverkConsumer.hentLandKoder())
    }

    suspend fun hentValuta(): Array<KodeOgTekstDto> {
        return toSortedKodeOgTekstArray(kodeverkConsumer.hentValuta())
    }

    suspend fun hentPostnummer(): Array<KodeOgTekstDto> {
        return toSortedKodeOgTekstArray(kodeverkConsumer.hentPostnummer())
    }

    private fun toSortedKodeOgTekstArray(kodeverk: Kodeverk): Array<KodeOgTekstDto> {
        return kodeverk.koder
            .filter { kode -> kode.betydninger.isNotEmpty() }
            .map { kode -> KodeOgTekstDto.fromKode(kode) }
            .sortedBy { it.tekst }
            .toTypedArray()
    }
}
