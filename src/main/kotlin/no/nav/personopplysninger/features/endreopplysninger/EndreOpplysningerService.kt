package no.nav.personopplysninger.features.endreopplysninger

import no.nav.personopplysninger.features.endreopplysninger.domain.adresse.*
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.EndringKontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.kontonummer.Kontonummer
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.EndringTelefon
import no.nav.personopplysninger.features.endreopplysninger.domain.telefon.Telefonnummer
import no.nav.personopplysninger.features.kodeverk.KodeverkConsumer
import no.nav.personopplysninger.features.kodeverk.api.GetKodeverkKoderBetydningerResponse
import no.nav.personopplysninger.features.kodeverk.api.KodeOgTekstDto
import no.nav.personopplysninger.features.kodeverk.api.RetningsnummerDTO
import no.nav.personopplysninger.features.sts.STSConsumer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EndreOpplysningerService @Autowired constructor(
        private var stsConsumer: STSConsumer,
        private var personMottakConsumer: PersonMottakConsumer,
        private var kodeverkConsumer: KodeverkConsumer
) {

    private val log = LoggerFactory.getLogger(EndreOpplysningerService::class.java)

    fun endreTelefonnummer(fnr: String, telefonnummer: Telefonnummer, httpMethod: String): EndringTelefon {
        return personMottakConsumer.endreTelefonnummer(fnr, telefonnummer, getSystembrukerToken(), httpMethod)
    }

    fun endreKontonummer(fnr: String, kontonummer: Kontonummer): EndringKontonummer {
        return personMottakConsumer.endreKontonummer(fnr, kontonummer, getSystembrukerToken())
    }

    fun endreGateadresse(fnr: String, gateadresse: Gateadresse): EndringGateadresse {
        return personMottakConsumer.endreGateadresse(fnr, gateadresse, getSystembrukerToken())
    }

    fun endreStedsadresse(fnr: String, stedsadresse: Stedsadresse): EndringStedsadresse {
        return personMottakConsumer.endreStedsadresse(fnr, stedsadresse, getSystembrukerToken())
    }

    fun endrePostboksadresse(fnr: String, postboksadresse: Postboksadresse): EndringPostboksadresse {
        return personMottakConsumer.endrePostboksadresse(fnr, postboksadresse, getSystembrukerToken())
    }

    fun endreUtenlandsadresse(fnr: String, utenlandsadresse: Utenlandsadresse): EndringUtenlandsadresse {
        return personMottakConsumer.endreUtenlandsadresse(fnr, utenlandsadresse, getSystembrukerToken())
    }

    fun hentRetningsnumre(): Array<RetningsnummerDTO> {
        return kodeverkConsumer.hentRetningsnumre().betydninger
                .map { entry -> RetningsnummerDTO(entry.key, entry.value.first().beskrivelser.entries.first().value.tekst) }
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

    private fun toSortedKodeOgTekstArray(koder: GetKodeverkKoderBetydningerResponse): Array<KodeOgTekstDto> {
        return koder.betydninger
                .filter { entry -> entry.value.isNotEmpty() }
                .map { entry -> KodeOgTekstDto(entry.key, entry.value.first().beskrivelser.entries.first().value.tekst) }
                .sortedBy { it.tekst }
                .toTypedArray()
    }

    private fun getSystembrukerToken(): String? {
        return stsConsumer.token?.access_token
    }

}