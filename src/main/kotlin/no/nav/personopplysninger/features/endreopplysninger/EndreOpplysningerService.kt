package no.nav.personopplysninger.features.endreopplysninger

import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.*
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
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EndreOpplysningerService (
        private var stsConsumer: STSConsumer,
        private var personMottakConsumer: PersonMottakConsumer,
        private var kodeverkConsumer: KodeverkConsumer,
        private var pdlService: PdlService
) {

    private val log = LoggerFactory.getLogger(EndreOpplysningerService::class.java)

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

        return personMottakConsumer.endreTelefonnummer(fnr, slettNummerPayload(fnr, telefonnummer, opplysningsId), systemToken)
    }

    fun endreKontonummer(fnr: String, kontonummer: Kontonummer): EndringKontonummer {
        return personMottakConsumer.endreKontonummer(fnr, kontonummer, systemToken)
    }

    fun endreGateadresse(fnr: String, gateadresse: Gateadresse): EndringGateadresse {
        return personMottakConsumer.endreGateadresse(fnr, gateadresse, systemToken)
    }

    fun endreStedsadresse(fnr: String, stedsadresse: Stedsadresse): EndringStedsadresse {
        return personMottakConsumer.endreStedsadresse(fnr, stedsadresse, systemToken)
    }

    fun endrePostboksadresse(fnr: String, postboksadresse: Postboksadresse): EndringPostboksadresse {
        return personMottakConsumer.endrePostboksadresse(fnr, postboksadresse, systemToken)
    }

    fun endreUtenlandsadresse(fnr: String, utenlandsadresse: Utenlandsadresse): EndringUtenlandsadresse {
        return personMottakConsumer.endreUtenlandsadresse(fnr, utenlandsadresse, systemToken)
    }

    fun opphoerAdresse(fnr: String, kontaktadresseType: KontaktadresseType): EndringOpphoerAdresse {
        return personMottakConsumer.opphoerKontaktadresse(fnr,  kontaktadresseType, systemToken)
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
        return toSortedKodeOgTekstArray(kodeverkConsumer.hentPostnummer());
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
