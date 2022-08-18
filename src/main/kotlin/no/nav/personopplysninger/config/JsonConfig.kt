package no.nav.personopplysninger.config

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Adresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Matrikkeladresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.PostAdresseIFrittFormat
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Postboksadresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Ukjentbosted
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.UtenlandskAdresse
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.UtenlandskAdresseIFrittFormat
import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.Vegadresse

fun jsonConfig(): Json {
    return Json {
        this.ignoreUnknownKeys = true
        this.encodeDefaults = true
        this.prettyPrint = true
        this.isLenient = true
        serializersModule = SerializersModule {
            polymorphic(Adresse::class) {
                subclass(Vegadresse::class)
                subclass(PostAdresseIFrittFormat::class)
                subclass(Postboksadresse::class)
                subclass(UtenlandskAdresse::class)
                subclass(UtenlandskAdresseIFrittFormat::class)
                subclass(Ukjentbosted::class)
                subclass(Matrikkeladresse::class)
            }
        }
    }
}
