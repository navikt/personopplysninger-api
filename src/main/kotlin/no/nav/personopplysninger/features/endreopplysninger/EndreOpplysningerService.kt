package no.nav.personopplysninger.features.endreopplysninger

import no.nav.personopplysninger.consumer.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.consumer.kodeverk.domain.KodeOgTekstDto
import no.nav.personopplysninger.consumer.kodeverk.domain.Kodeverk
import no.nav.personopplysninger.consumer.kodeverk.domain.RetningsnummerDTO
import no.nav.personopplysninger.consumer.kontoregister.KontoregisterConsumer
import no.nav.personopplysninger.consumer.kontoregister.domain.OppdaterKonto
import no.nav.personopplysninger.consumer.kontoregister.domain.UtenlandskKontoInfo
import no.nav.personopplysninger.consumer.pdl.PdlService
import no.nav.personopplysninger.consumer.personmottak.PersonMottakConsumer
import no.nav.personopplysninger.consumer.personmottak.domain.kontaktadresse.EndringKontaktadresse
import no.nav.personopplysninger.consumer.personmottak.domain.kontaktadresse.slettKontaktadressePayload
import no.nav.personopplysninger.consumer.personmottak.domain.telefon.EndringTelefon
import no.nav.personopplysninger.consumer.personmottak.domain.telefon.Telefonnummer
import no.nav.personopplysninger.consumer.personmottak.domain.telefon.endreNummerPayload
import no.nav.personopplysninger.consumer.personmottak.domain.telefon.slettNummerPayload
import no.nav.personopplysninger.features.endreopplysninger.dto.Kontonummer
import no.nav.personopplysninger.consumer.pdlmottak.PdlMottakConsumer
import no.nav.personopplysninger.consumer.pdlmottak.domain.kontaktadresse.EndringKontaktadresse
import no.nav.personopplysninger.consumer.pdlmottak.domain.kontaktadresse.slettKontaktadressePayload
import no.nav.personopplysninger.consumer.pdlmottak.domain.kontonummer.EndringKontonummer
import no.nav.personopplysninger.consumer.pdlmottak.domain.kontonummer.Kontonummer
import no.nav.personopplysninger.consumer.pdlmottak.domain.telefon.EndringTelefon
import no.nav.personopplysninger.consumer.pdlmottak.domain.telefon.Telefonnummer
import no.nav.personopplysninger.consumer.pdlmottak.domain.telefon.endreNummerPayload
import no.nav.personopplysninger.consumer.pdlmottak.domain.telefon.slettNummerPayload
import org.springframework.stereotype.Service

@Service
class EndreOpplysningerService(
    private var pdlMottakConsumer: PdlMottakConsumer,
    private var kodeverkConsumer: KodeverkConsumer,
    private var kontoregisterConsumer: KontoregisterConsumer,
    private var pdlService: PdlService
) {

    fun endreTelefonnummer(fnr: String, telefonnummer: Telefonnummer): EndringTelefon {
        if (!setOf(1, 2).contains(telefonnummer.prioritet)) {
            throw RuntimeException("Støtter kun prioritet [1, 2] eller type ['HJEM', 'MOBIL']")
        } else {
            return pdlMottakConsumer.endreTelefonnummer(fnr, endreNummerPayload(fnr, telefonnummer))
        }
    }

    fun slettTelefonNummer(fnr: String, telefonnummer: Telefonnummer): EndringTelefon {
        val opplysningsId =
            pdlService.getOpplysningsIdForTelefon(fnr, telefonnummer.landskode!!, telefonnummer.nummer!!)
                ?: throw RuntimeException("Kan ikke slette nummer som ikke eksisterer: ${telefonnummer.landskode}, ${telefonnummer.nummer}")

        return pdlMottakConsumer.slettPersonopplysning(
            fnr,
            slettNummerPayload(fnr, opplysningsId),
            EndringTelefon::class.java
        )
    }

    fun endreKontonummer(fnr: String, kontonummer: Kontonummer) {
        val request = OppdaterKonto(
            kontohaver = fnr,
            nyttKontonummer = kontonummer.value,
            utenlandskKontoInfo = kontonummer.utenlandskKontoInformasjon?.let {
                UtenlandskKontoInfo(
                    bankLandkode = kontonummer.utenlandskKontoInformasjon.landkode,
                    valutakode = kontonummer.utenlandskKontoInformasjon.valuta!!,
                    swiftBicKode = kontonummer.utenlandskKontoInformasjon.swift
                )
            }
        )
        kontoregisterConsumer.endreKontonummer(request)
    }

    fun slettKontaktadresse(fnr: String): EndringKontaktadresse {
        val opplysningsId = pdlService.getOpplysningsIdForKontaktadresse(fnr)
            ?: throw RuntimeException("Fant ingen kontaktadresser som kan slettes")

        return pdlMottakConsumer.slettPersonopplysning(
            fnr,
            slettKontaktadressePayload(fnr, opplysningsId),
            EndringKontaktadresse::class.java
        )
    }

    fun hentRetningsnumre(): Array<RetningsnummerDTO> {
        return kodeverkConsumer.hentRetningsnumre().koder
            .map { kode ->
                RetningsnummerDTO(
                    kode.navn,
                    kode.betydninger.first().beskrivelser.entries.first().value.tekst
                )
            }
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
            .map { kode -> KodeOgTekstDto.fromKode(kode) }
            .sortedBy { it.tekst }
            .toTypedArray()
    }
}
