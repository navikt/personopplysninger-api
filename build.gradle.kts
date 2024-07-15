import com.expediagroup.graphql.plugin.gradle.config.GraphQLSerializer
import com.expediagroup.graphql.plugin.gradle.graphql
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    val kotlinVersion = "2.0.0"
    val shadowVersion = "8.1.1"
    val versionsVersion = "0.51.0"
    val graphqlVersion = "7.1.4"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("com.expediagroup.graphql") version graphqlVersion
    id("com.github.johnrengelman.shadow") version shadowVersion
    id("com.github.ben-manes.versions") version versionsVersion // ./gradlew dependencyUpdates to check for new versions
    application
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://maven.pkg.github.com/navikt/tms-varsel-authority") {
        credentials {
            username = System.getenv("GITHUB_ACTOR") ?: "x-access-token"
            password = System.getenv("GITHUB_TOKEN") ?: project.findProperty("githubPassword") as String
        }
    }
}

dependencies {
    val auth0JwtVersion = "4.4.0"
    val caffeineVersion = "3.1.8"
    val graphqlVersion = "7.1.1"
    val kafkaVersion = "3.7.0"
    val ktorVersion = "2.3.12"
    val logbackVersion = "1.5.6"
    val logstashVersion = "7.4"
    val micrometerVersion = "1.13.1"
    val mockkVersion = "1.13.11"
    val navSecurityVersion = "5.0.1"
    val kotlinVersion = "2.0.0"
    val tmsKtorTokenSupportVersion = "4.1.2"
    val tmsVarselBuilderVersion = "1.0.4"

    implementation("com.auth0:java-jwt:$auth0JwtVersion")
    implementation("com.github.ben-manes.caffeine:caffeine:$caffeineVersion")
    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
    implementation("no.nav.tms.varsel:kotlin-builder:$tmsVarselBuilderVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("io.ktor:ktor-server-forwarded-header:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-apache:$ktorVersion")
    implementation("io.ktor:ktor-server-metrics-micrometer:$ktorVersion")
    implementation("com.expediagroup:graphql-kotlin-ktor-client:$graphqlVersion")
    implementation("io.micrometer:micrometer-registry-prometheus:$micrometerVersion")
    implementation("no.nav.security:token-validation-ktor-v2:$navSecurityVersion")
    implementation("no.nav.tms.token.support:tokendings-exchange:$tmsKtorTokenSupportVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlinVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("io.ktor:ktor-client-mock:$ktorVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
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

graphql {
    client {
        packageName = "no.nav.pdl.generated.dto"
        serializer = GraphQLSerializer.KOTLINX
        sdlEndpoint = "https://navikt.github.io/pdl/pdl-api-sdl.graphqls"
    }
}
