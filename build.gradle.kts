import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    val versions = object {
        val kotlin = "1.9.0"
        val shadow = "8.1.1"
        val versions = "0.50.0"
    }

    kotlin("jvm") version (versions.kotlin)
    kotlin("plugin.allopen") version (versions.kotlin)
    kotlin("plugin.serialization") version (versions.kotlin)

    id("com.github.johnrengelman.shadow") version (versions.shadow)
    id("com.github.ben-manes.versions") version (versions.versions) // ./gradlew dependencyUpdates to check for new versions
    application
}

kotlin {
    jvmToolchain(17)
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://maven.pkg.github.com/navikt/tms-varsel-authority") {
        credentials {
            username = System.getenv("GITHUB_ACTOR")?: "x-access-token"
            password = System.getenv("GITHUB_TOKEN")?: project.findProperty("githubPassword") as String
        }
    }

}

dependencies {
    val versions = object {
        val auth0Jwt = "4.4.0"
        val caffeine = "3.1.8"
        val kafka = "3.6.1"
        val ktor = "2.3.6"
        val logback = "1.4.11"
        val logstash = "7.4"
        val micrometer = "1.12.0"
        val mockk = "1.13.8"
        val navSecurity = "3.1.8"
        val kotlin = "1.9.0"
        val tmsKtorTokenSupport = "3.0.0"
        val tmsVarselBuilder = "1.0.1"
    }

    implementation("com.auth0:java-jwt:${versions.auth0Jwt}")
    implementation("com.github.ben-manes.caffeine:caffeine:${versions.caffeine}")
    implementation("org.apache.kafka:kafka-clients:${versions.kafka}")
    implementation("no.nav.tms.varsel:kotlin-builder:${versions.tmsVarselBuilder}")
    implementation("io.ktor:ktor-serialization:${versions.ktor}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${versions.ktor}")
    implementation("io.ktor:ktor-server-netty:${versions.ktor}")
    implementation("io.ktor:ktor-server-call-logging:${versions.ktor}")
    implementation("io.ktor:ktor-server-core:${versions.ktor}")
    implementation("io.ktor:ktor-server-cors:${versions.ktor}")
    implementation("io.ktor:ktor-server-forwarded-header:${versions.ktor}")
    implementation("io.ktor:ktor-server-status-pages:${versions.ktor}")
    implementation("io.ktor:ktor-server-content-negotiation:${versions.ktor}")
    implementation("io.ktor:ktor-server-auth:${versions.ktor}")
    implementation("io.ktor:ktor-server-auth-jwt:${versions.ktor}")
    implementation("io.ktor:ktor-client-content-negotiation:${versions.ktor}")
    implementation("io.ktor:ktor-client-apache:${versions.ktor}")
    implementation("io.ktor:ktor-server-metrics-micrometer:${versions.ktor}")
    implementation("io.micrometer:micrometer-registry-prometheus:${versions.micrometer}")
    implementation("no.nav.security:token-validation-ktor-v2:${versions.navSecurity}")
    implementation("com.github.navikt.tms-ktor-token-support:token-support-tokendings-exchange:${versions.tmsKtorTokenSupport}")
    implementation("net.logstash.logback:logstash-logback-encoder:${versions.logstash}")
    implementation("ch.qos.logback:logback-classic:${versions.logback}")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:${versions.kotlin}")
    testImplementation("io.ktor:ktor-server-test-host:${versions.ktor}")
    testImplementation("io.ktor:ktor-client-mock:${versions.ktor}")
    testImplementation("io.mockk:mockk:${versions.mockk}")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

tasks {
    withType<Test> {
        useJUnitPlatform()
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
            events("passed", "skipped", "failed")
        }
    }
}
