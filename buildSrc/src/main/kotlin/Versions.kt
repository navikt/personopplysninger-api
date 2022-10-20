object Auth0 {
    private const val version = "4.1.0"
    private const val groupId = "com.auth0"

    const val jwt = "$groupId:java-jwt:$version"
}

object Caffeine {

    private const val version = "3.1.1"
    private const val groupId = "com.github.ben-manes.caffeine"

    const val caffeine = "$groupId:caffeine:$version"
}

object Finn {
    private const val version = "4.4.1"
    private const val groupId = "no.finn.unleash"

    const val unleashClient = "$groupId:unleash-client-java:$version"
}

object Kotlin {
    const val version = "1.7.20"
    private const val groupId = "org.jetbrains.kotlin"

    const val junit5 = "$groupId:kotlin-test-junit5:$version"
}

object Ktor {
    private const val version = "2.1.2"
    private const val groupId = "io.ktor"

    const val metricsMicrometer = "$groupId:ktor-server-metrics-micrometer:$version"
    const val serialization = "$groupId:ktor-serialization:$version"
    const val serializationKotlinx = "$groupId:ktor-serialization-kotlinx-json:$version"
    const val serverNetty = "$groupId:ktor-server-netty:$version"
    const val serverCallLogging = "$groupId:ktor-server-call-logging:$version"
    const val serverCore = "$groupId:ktor-server-core:$version"
    const val serverCors = "$groupId:ktor-server-cors:$version"
    const val serverStatusPages = "$groupId:ktor-server-status-pages:$version"
    const val serverContentNegotiation = "$groupId:ktor-server-content-negotiation:$version"
    const val serverAuth = "$groupId:ktor-server-auth:$version"
    const val serverTestHost = "$groupId:ktor-server-test-host:$version"
    const val clientContentNegotiation = "$groupId:ktor-client-content-negotiation:$version"
    const val clientApache = "$groupId:ktor-client-apache:$version"
    const val clientMock = "$groupId:ktor-client-mock:$version"
}

object Logback {
    private const val version = "1.4.4"
    const val classic = "ch.qos.logback:logback-classic:$version"
}

object Logstash {
    private const val version = "7.2"
    private const val groupId = "net.logstash.logback"

    const val logbackEncoder = "$groupId:logstash-logback-encoder:$version"
}

object Micrometer {
    private const val version = "1.9.5"
    const val registryPrometheus = "io.micrometer:micrometer-registry-prometheus:$version"
}

object Mockk {
    private const val version = "1.13.2"
    const val mockk = "io.mockk:mockk:$version"
}

object NAV {
    object Security {
        private const val version = "2.1.6"
        private const val groupId = "no.nav.security"

        const val tokenValidationKtor = "$groupId:token-validation-ktor-v2:$version"
    }

    object Common {
        private const val version = "2.2022.10.20_07.46-64a2e6bdf2d0"
        private const val groupId = "no.nav.common"
        const val featureToggle = "$groupId:feature-toggle:$version"
    }
}

object Shadow {
    const val version = "7.1.2"
    const val pluginId = "com.github.johnrengelman.shadow"
}

object TmsKtorTokenSupport {
    private const val version = "2.0.0"
    private const val groupId = "com.github.navikt.tms-ktor-token-support"

    const val tokendingsExchange = "$groupId:token-support-tokendings-exchange:$version"
}

object Versions {
    const val version = "0.43.0"
    const val pluginId = "com.github.ben-manes.versions"
}