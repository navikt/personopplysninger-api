package no.nav.personopplysninger.config

import io.prometheus.client.Counter

object MetricsCollector {

    val NORSK_KONTONUMMER_COUNTER = Counter.build()
        .name("endring_norsk_kontonummer")
        .help("Antall endringer av norske kontonummer")
        .register()

    val UTENLANDSK_KONTONUMMER_COUNTER = Counter.build()
        .name("endring_utenlandsk_kontonummer")
        .help("Antall endringer av utenlandske kontonummer")
        .register()
}