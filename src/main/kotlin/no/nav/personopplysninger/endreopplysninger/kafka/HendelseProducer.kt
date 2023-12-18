package no.nav.personopplysninger.endreopplysninger.kafka

import no.nav.tms.varsel.action.EksternKanal
import no.nav.tms.varsel.action.EksternVarslingBestilling
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
        val internVarslingstekst = internVarslingstekst()
        val eksternVarslingstekst = eksternVarslingstekst(internVarslingstekst)

        return VarselActionBuilder.opprett {
            type = Varseltype.Beskjed
            varselId = eventId
            sensitivitet = Sensitivitet.Substantial
            ident = fnr
            tekster += Tekst(
                spraakkode = "nb",
                tekst = internVarslingstekst,
                default = true
            )
            link = "https://www.nav.no"
            aktivFremTil = ZonedDateTime.now(ZoneId.of("Z")).plusDays(14)
            eksternVarsling = EksternVarslingBestilling(
                prefererteKanaler = listOf(EksternKanal.SMS, EksternKanal.EPOST),
                epostVarslingstittel = VARSLINGSTITTEL,
                epostVarslingstekst = eksternVarslingstekst,
                smsVarslingstekst = eksternVarslingstekst,
            )
        }
    }

    // Varsling i dekoratøren
    private fun internVarslingstekst(): String {
        val timestamp = LocalDateTime.now()
        val dayOfMonth = timestamp.dayOfMonth
        val month = monthNamesMap[timestamp.monthValue]
        val time = timestamp.format(timeFormatter)

        return "Kontonummeret ditt hos NAV ble endret $dayOfMonth. $month kl. $time. Ring oss om dette ikke stemmer på tlf. 55 55 33 33."
    }

    // Varsling på sms og e-post
    private fun eksternVarslingstekst(baseText: String): String {
        return "Hei! $baseText Hilsen NAV"
    }

    companion object {
        const val VARSLINGSTITTEL =
            "Du har endret kontonummeret ditt hos NAV"

        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm")

        val monthNamesMap = mapOf(
            1 to "januar",
            2 to "februar",
            3 to "mars",
            4 to "april",
            5 to "mai",
            6 to "juni",
            7 to "juli",
            8 to "august",
            9 to "september",
            10 to "oktober",
            11 to "november",
            12 to "desember",
        )
    }
}
