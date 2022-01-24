package no.nav.personopplysninger.features.personalia.dto.outbound.adresse

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes(
    JsonSubTypes.Type(value = Vegadresse::class),
    JsonSubTypes.Type(value = PostAdresseIFrittFormat::class),
    JsonSubTypes.Type(value = Postboksadresse::class),
    JsonSubTypes.Type(value = UtenlandskAdresse::class),
    JsonSubTypes.Type(value = UtenlandskAdresseIFrittFormat::class),
    JsonSubTypes.Type(value = Ukjentbosted::class),
    JsonSubTypes.Type(value = Matrikkeladresse::class)
)
interface Adresse {
    val type: AdresseType
}