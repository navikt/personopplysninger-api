object Auth0 {
    private const val version = "4.0.0"
    private const val groupId = "com.auth0"

    const val jwt = "$groupId:java-jwt:$version"
}

object DittNAV {
    object Common {
        private const val version = "2022.04.19-11.11-1043a85c4f6f"
        private const val groupId = "com.github.navikt.dittnav-common-lib"

        const val logging = "$groupId:dittnav-common-logging:$version"
    }
}

object Finn {
    private const val version = "4.4.1"
    private const val groupId = "no.finn.unleash"

    const val unleashClient = "$groupId:unleash-client-java:$version"
}

object Jackson {
    private const val version = "2.13.2"

    const val kotlinModule = "com.fasterxml.jackson.module:jackson-module-kotlin:$version"
    const val datatypeJsr310 = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$version"
}

object Kotlin {
    const val version = "1.7.10"
    private const val groupId = "org.jetbrains.kotlin"

    const val jdk8 = "$groupId:kotlin-stdlib-jdk8:$version"
    const val junit5 = "$groupId:kotlin-test-junit5:$version"
}

object Ktor {
    private const val version = "2.1.0"
    private const val groupId = "io.ktor"

    const val serverNetty = "$groupId:ktor-server-netty:$version"
    const val serverCallLogging = "$groupId:ktor-server-call-logging:$version"
    const val serverCore = "$groupId:ktor-server-core:$version"
    const val serialization = "$groupId:ktor-serialization:$version"
    const val serializationGson = "$groupId:ktor-serialization-gson:$version"
    const val serverCors = "$groupId:ktor-server-cors:$version"
    const val serverStatusPages = "$groupId:ktor-server-status-pages:$version"
    const val serverContentNegotiation = "$groupId:ktor-server-content-negotiation:$version"
    const val serverAuth = "$groupId:ktor-server-auth:$version"
    const val serverTestHost = "$groupId:ktor-server-test-host:$version"
    const val clientContentNegotiation = "$groupId:ktor-client-content-negotiation:$version"
    const val clientApache = "$groupId:ktor-client-apache:$version"
}

object Logback {
    private const val version = "1.2.11"
    const val classic = "ch.qos.logback:logback-classic:$version"
}

object Logstash {
    private const val version = "7.2"
    private const val groupId = "net.logstash.logback"

    const val logbackEncoder = "$groupId:logstash-logback-encoder:$version"
}

object NAV {
    object Security {
        private const val version = "2.1.3"
        private const val groupId = "no.nav.security"

        const val tokenValidationKtor = "$groupId:token-validation-ktor-v2:$version"
    }

    object Common {
        private const val version = "2.2022.05.13_07.24-6eadb37dec31"
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
    const val version = "0.42.0"
    const val pluginId = "com.github.ben-manes.versions"
}