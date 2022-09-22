package no.nav.personopplysninger.config

import io.prometheus.client.CollectorRegistry
import io.prometheus.client.Counter

class MetricsCollector(registry: CollectorRegistry) {

    private val NAMESPACE = "personbruker"

    val ENDRE_TELEFONNUMMER_COUNTER = Counter.build()
        .name("endring_telefonnummer")
        .namespace(NAMESPACE)
        .help("Antall endringer av telefonnummer")
        .register(registry)

    val SLETT_TELEFONNUMMER_COUNTER = Counter.build()
        .name("sletting_telefonnummer")
        .namespace(NAMESPACE)
        .help("Antall slettinger av telefonnummer")
        .register(registry)

    val ENDRE_NORSK_KONTONUMMER_COUNTER = Counter.build()
        .name("endring_norsk_kontonummer")
        .namespace(NAMESPACE)
        .help("Antall endringer av norske kontonummer")
        .register(registry)

    val ENDRE_UTENLANDSK_KONTONUMMER_COUNTER = Counter.build()
        .name("endring_utenlandsk_kontonummer")
        .namespace(NAMESPACE)
        .help("Antall endringer av utenlandske kontonummer")
        .register(registry)

    val SLETT_KONTAKTADRESSE_COUNTER = Counter.build()
        .name("sletting_kontaktadresse")
        .namespace(NAMESPACE)
        .help("Antall slettinger av kontaktadresser")
        .register(registry)
}