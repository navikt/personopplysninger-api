package no.nav.personopplysninger.config

import io.prometheus.client.CollectorRegistry
import io.prometheus.client.Counter

class MetricsCollector(registry: CollectorRegistry) {

    private val NAMESPACE = "personbruker"

    val NORSK_KONTONUMMER_COUNTER = Counter.build()
        .name("endring_norsk_kontonummer")
        .namespace(NAMESPACE)
        .help("Antall endringer av norske kontonummer")
        .register(registry)

    val UTENLANDSK_KONTONUMMER_COUNTER = Counter.build()
        .name("endring_utenlandsk_kontonummer")
        .namespace(NAMESPACE)
        .help("Antall endringer av utenlandske kontonummer")
        .register(registry)
}