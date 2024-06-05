package no.nav.personopplysninger.endreopplysninger

import no.nav.personopplysninger.consumer.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.consumer.kontoregister.KontoregisterConsumer
import no.nav.personopplysninger.consumer.kontoregister.dto.request.OppdaterKonto
import no.nav.personopplysninger.consumer.kontoregister.dto.request.UtenlandskKontoInfo
import no.nav.personopplysninger.consumer.kontoregister.dto.response.Kontonummer
import no.nav.personopplysninger.consumer.kontoregister.dto.response.Landkode
import no.nav.personopplysninger.consumer.kontoregister.dto.response.Valutakode
import no.nav.personopplysninger.consumer.pdl.PdlConsumer
import no.nav.personopplysninger.consumer.pdlmottak.PdlMottakConsumer
import no.nav.personopplysninger.consumer.pdlmottak.dto.inbound.Telefonnummer
import no.nav.personopplysninger.consumer.pdlmottak.dto.outbound.Endring
import no.nav.personopplysninger.endreopplysninger.dto.Postnummer
import no.nav.personopplysninger.endreopplysninger.dto.Retningsnummer
import no.nav.personopplysninger.endreopplysninger.extensions.findOpplysningsId
import no.nav.personopplysninger.endreopplysninger.kafka.HendelseProducer
import org.slf4j.LoggerFactory
import java.util.*

class EndreOpplysningerService(
    private val pdlConsumer: PdlConsumer,
    private val pdlMottakConsumer: PdlMottakConsumer,
    private val kodeverkConsumer: KodeverkConsumer,
    private val kontoregisterConsumer: KontoregisterConsumer,
    private val hendelseProducer: HendelseProducer
) {
    private val logger = LoggerFactory.getLogger(EndreOpplysningerService::class.java)

    suspend fun endreTelefonnummer(token: String, fnr: String, telefonnummer: Telefonnummer): Endring {
        if (!setOf(1, 2).contains(telefonnummer.prioritet)) {
            throw RuntimeException("Støtter kun prioritet [1, 2] eller type ['HJEM', 'MOBIL']")
        } else {
            return pdlMottakConsumer.endreTelefonnummer(token, fnr, telefonnummer)
        }
    }

    suspend fun slettTelefonNummer(token: String, fnr: String, telefonnummer: Telefonnummer): Endring {
        val opplysningsId = pdlConsumer.hentTelefon(token, fnr)
            .findOpplysningsId(telefonnummer.landskode, telefonnummer.nummer)
            ?: throw RuntimeException("Fant ikke oppgitt telefonnummer")

        return pdlMottakConsumer.slettTelefonnummer(token, fnr, opplysningsId)
    }

    suspend fun slettKontaktadresse(token: String, fnr: String): Endring {
        val opplysningsId = pdlConsumer.hentKontaktadresse(token, fnr).findOpplysningsId()
            ?: throw RuntimeException("Fant ingen kontaktadresser som kan slettes")

        return pdlMottakConsumer.slettKontaktadresse(token, fnr, opplysningsId)
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
        try {
            hendelseProducer.sendVarselHendelse(fnr = fnr, eventId = UUID.randomUUID().toString())
        } catch (e: Exception) {
            logger.error("Feil ved publisering til brukervarsel-topic", e)
        }
    }

    suspend fun hentRetningsnumre(): List<Retningsnummer> {
        return kodeverkConsumer.hentRetningsnumre().betydninger.map(Retningsnummer::fromBetydning).sortedBy { it.land }
    }

    suspend fun hentLandkoder(): List<Landkode> {
        return kontoregisterConsumer.hentLandkoder()
    }

    suspend fun hentValutakoder(): List<Valutakode> {
        return kontoregisterConsumer.hentValutakoder()
    }

    suspend fun hentPostnummer(): List<Postnummer> {
        return kodeverkConsumer.hentPostnummer().betydninger.map(Postnummer::fromBetydning).sortedBy { it.tekst }
    }
}
