package no.nav.personopplysninger.features.endreopplysninger

import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.*
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.EndringKontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.Kontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.EndringTelefon
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.Telefonnummer
import no.nav.personopplysninger.features.sts.STSConsumer
import no.nav.personopplysninger.oppslag.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.oppslag.kodeverk.api.KodeOgTekstDto
import no.nav.personopplysninger.oppslag.kodeverk.api.Kodeverk
import no.nav.personopplysninger.oppslag.kodeverk.api.RetningsnummerDTO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EndreOpplysningerService (
        private var stsConsumer: STSConsumer,
        private var personMottakConsumer: PersonMottakConsumer,
        private var kodeverkConsumer: KodeverkConsumer
) {

    private val log = LoggerFactory.getLogger(EndreOpplysningerService::class.java)

    fun endreTelefonnummer(fnr: String, telefonnummer: Telefonnummer, httpMethod: String): EndringTelefon {
        return personMottakConsumer.endreTelefonnummer(fnr, telefonnummer, brukerToken, httpMethod)
    }

    fun endreKontonummer(fnr: String, kontonummer: Kontonummer): EndringKontonummer {
        return personMottakConsumer.endreKontonummer(fnr, kontonummer, brukerToken)
    }

    fun endreGateadresse(fnr: String, gateadresse: Gateadresse): EndringGateadresse {
        return personMottakConsumer.endreGateadresse(fnr, gateadresse, brukerToken)
    }

    fun endreStedsadresse(fnr: String, stedsadresse: Stedsadresse): EndringStedsadresse {
        return personMottakConsumer.endreStedsadresse(fnr, stedsadresse, brukerToken)
    }

    fun endrePostboksadresse(fnr: String, postboksadresse: Postboksadresse): EndringPostboksadresse {
        return personMottakConsumer.endrePostboksadresse(fnr, postboksadresse, brukerToken)
    }

    fun endreUtenlandsadresse(fnr: String, utenlandsadresse: Utenlandsadresse): EndringUtenlandsadresse {
        return personMottakConsumer.endreUtenlandsadresse(fnr, utenlandsadresse, brukerToken)
    }

    fun opphoerAdresse(fnr: String, kontaktadresseType: KontaktadresseType): EndringOpphoerAdresse {
        return personMottakConsumer.opphoerKontaktadresse(fnr,  kontaktadresseType, brukerToken)
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

    private inline val brukerToken: String get() = stsConsumer.token.access_token
}
