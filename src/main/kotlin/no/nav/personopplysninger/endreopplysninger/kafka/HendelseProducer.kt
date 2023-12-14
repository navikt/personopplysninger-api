package no.nav.personopplysninger.endreopplysninger.kafka

import no.nav.tms.varsel.action.EksternKanal
import no.nav.tms.varsel.action.EksternVarslingBestilling
import no.nav.tms.varsel.action.Sensitivitet
import no.nav.tms.varsel.action.Tekst
import no.nav.tms.varsel.action.Varseltype
import no.nav.tms.varsel.builder.VarselActionBuilder
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import java.time.ZoneId
import java.time.ZonedDateTime

class HendelseProducer(
    private val kafkaProducer: Producer<String, String>,
    private val topicName: String
) {
    fun sendVarselHendelse(fnr: String, eventId: String) {
        val hendelse = createHendelse(fnr, eventId)
        val producerRecord = ProducerRecord(topicName, eventId, hendelse)
        kafkaProducer.send(producerRecord)
    }

    private fun createHendelse(fnr: String, eventId: String): String {
        return VarselActionBuilder.opprett {
            type = Varseltype.Beskjed
            varselId = eventId
            sensitivitet = Sensitivitet.Substantial
            ident = fnr
            tekster += Tekst(
                spraakkode = "nb",
                tekst = VARSLINGSTEKST,
                default = true
            )
            link = "https://www.nav.no"
            aktivFremTil = ZonedDateTime.now(ZoneId.of("Z")).plusDays(14)
            eksternVarsling = EksternVarslingBestilling(
                prefererteKanaler = listOf(EksternKanal.SMS, EksternKanal.EPOST),
                epostVarslingstekst = VARSLINGSTEKST,
                epostVarslingstittel = VARSLINGSTITTEL,
                smsVarslingstekst = VARSLINGSTEKST
            )
        }
    }

    companion object {
        const val VARSLINGSTEKST =
            "Kontonummeret ditt på NAV er endret. Logg inn på NAV hvis det ikke var deg som endret."
        const val VARSLINGSTITTEL =
            "Kontonummeret ditt på NAV er endret"
    }
}
