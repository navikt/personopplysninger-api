object Tomakehurst {
    private const val version = "2.27.2"
    private const val groupId = "com.github.tomakehurst"

    const val wiremock = "$groupId:wiremock:$version"
}

object SpringCloud {
    private const val version = "3.1.2"
    private const val groupId = "org.springframework.cloud"

    const val stubRunner = "$groupId:spring-cloud-starter-contract-stub-runner:$version"
}

object Arrow {
    private const val version = "1.1.2"
    private const val groupId = "io.arrow-kt"

    const val core = "$groupId:arrow-core:$version"
}

object CommonsLogging {
    private const val version = "1.2"
    private const val groupId = "commons-logging"

    const val logging = "$groupId:commons-logging:$version"
}

object Finn {
    private const val version = "4.4.1"
    private const val groupId = "no.finn.unleash"

    const val unleashClient = "$groupId:unleash-client-java:$version"
}

object Jackson {
    private const val version = "2.13.2"
    private const val groupId = "com.fasterxml.jackson.module"

    const val kotlinModule = "$groupId:jackson-module-kotlin:$version"
}

object Jakarta {
    private const val version = "2.3.3"
    private const val groupId = "jakarta.xml.bind"

    const val xmlBindApi = "$groupId:jakarta.xml.bind-api:$version"
}

object Jaxb {
    private const val version = "2.3.6"
    private const val groupId = "org.glassfish.jaxb"

    const val runtime = "$groupId:jaxb-runtime:$version"
}

object Junit {
    private const val version = "5.8.2"
    private const val groupId = "org.junit.jupiter"
    const val api = "$groupId:junit-jupiter-api:$version"

    const val engine = "$groupId:junit-jupiter-engine:$version"
}

object Kotlin {
    const val version = "1.6.21"
    private const val groupId = "org.jetbrains.kotlin"

    const val jdk8 = "$groupId:kotlin-stdlib-jdk8:$version"
    const val junit5 = "$groupId:kotlin-test-junit5:$version"
}

object Kotlinx {
    const val version = "1.5.2"
    private const val groupId = "org.jetbrains.kotlinx"

    const val coroutinesCore = "$groupId:kotlinx-coroutines-core:$version"
}

object Logstash {
    private const val version = "7.1.1"
    private const val groupId = "net.logstash.logback"

    const val logbackEncoder = "$groupId:logstash-logback-encoder:$version"
}

object Micrometer {
    private const val version = "1.8.4"
    private const val groupId = "io.micrometer"

    const val registryPrometheus = "$groupId:micrometer-registry-prometheus:$version"
}

object Microutils {
    private const val version = "2.1.21"

    private const val groupId = "io.github.microutils"
    const val kotlinLogging = "$groupId:kotlin-logging:$version"
}

object Natpryce {
    private const val version = "1.6.10.0"
    private const val groupId = "com.natpryce"

    const val konfig = "$groupId:konfig:$version"
}

object NAV {
    object Security {
        private const val version = "2.0.20"
        private const val groupId = "no.nav.security"
        const val tokenValidatorCore = "$groupId:token-validation-core:$version"

        const val tokenValidatorJaxrs = "$groupId:token-validation-jaxrs:$version"
        const val tokenValidatorSpringTest = "$groupId:token-validation-spring-test:$version"
    }

    object Common {
        private const val version = "2.2022.05.13_07.24-6eadb37dec31"
        private const val groupId = "no.nav.common"
        const val rest = "$groupId:rest:$version"
        const val log = "$groupId:log:$version"
        const val featureToggle = "$groupId:feature-toggle:$version"
    }

}

object Shadow {
    const val version = "7.1.2"
    const val pluginId = "com.github.johnrengelman.shadow"
}

object Slf4j {
    private const val version = "1.7.36"
    private const val groupId = "org.slf4j"

    const val api = "$groupId:slf4j-api:$version"
}

object SpringBoot {
    private const val version = "2.6.6"
    private const val groupId = "org.springframework.boot"

    const val starterJetty = "$groupId:spring-boot-starter-jetty:$version"
    const val starterActuator = "$groupId:spring-boot-starter-actuator:$version"
    const val starterJersey = "$groupId:spring-boot-starter-jersey:$version"
    const val starterWeb = "$groupId:spring-boot-starter-web:$version"
    const val starterTest = "$groupId:spring-boot-starter-test:$version"
}

object Versions {
    const val version = "0.42.0"
    const val pluginId = "com.github.ben-manes.versions"
}