package no.nav.personopplysninger.endreopplysninger.kafka

import no.nav.tms.varsel.action.EksternKanal
import no.nav.tms.varsel.action.Sensitivitet
import no.nav.tms.varsel.action.Tekst
import no.nav.tms.varsel.action.Varseltype
import no.nav.tms.varsel.builder.VarselActionBuilder
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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
        val endringstidspunkt = LocalDateTime.now().toFormattedString()

        return VarselActionBuilder.opprett {
            type = Varseltype.Beskjed
            varselId = eventId
            sensitivitet = Sensitivitet.Substantial
            ident = fnr
            tekster += Tekst(
                spraakkode = "nb",
                tekst = dekoratorVarslingstekst(endringstidspunkt),
                default = true
            )
            link = "https://www.nav.no/person/personopplysninger/nb/#utbetaling"
            aktivFremTil = ZonedDateTime.now(ZoneId.of("Z")).plusDays(14)
            eksternVarsling {
                preferertKanal = EksternKanal.BETINGET_SMS
                epostVarslingstittel = VARSLINGSTITTEL
                epostVarslingstekst = epostVarslingstekst(endringstidspunkt)
                smsVarslingstekst = smsVarslingstekst(endringstidspunkt)
            }
        }
    }

    private fun dekoratorVarslingstekst(endringstidspunkt: String): String {
        return "Kontonummeret ditt hos NAV ble endret $endringstidspunkt. " +
                "Hvis det ikke var deg som endret, må du logge inn på NAV for å rette det. " +
                "Trenger du hjelp, kan du ringe oss på 55 55 33 33 eller kontakte oss i våre digitale kanaler."
    }

    private fun epostVarslingstekst(endringstidspunkt: String): String {
        return "Hei! Kontonummeret ditt hos NAV ble endret $endringstidspunkt. " +
                "Hvis det ikke var deg som endret, må du logge deg inn på NAV for å rette kontonummeret. " +
                "Trenger du hjelp, kan du ringe oss på 55 55 33 33 kl. 09:00–15:00. Hilsen NAV."
    }

    private fun smsVarslingstekst(endringstidspunkt: String): String {
        return "Kontonummeret ditt hos NAV ble endret $endringstidspunkt. " +
                "Hvis det er feil, må du logge inn på NAV for å rette det eller ringe oss på 55 55 33 33 fra 9-15."
    }

    private fun LocalDateTime.toFormattedString(): String {
        val dayOfMonth = this.dayOfMonth
        val month = this.monthValue
        val time = this.format(timeFormatter)

        return "$dayOfMonth.$month. kl. $time"
    }

    companion object {
        const val VARSLINGSTITTEL = "Du har endret kontonummeret ditt hos NAV"

        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    }
}
