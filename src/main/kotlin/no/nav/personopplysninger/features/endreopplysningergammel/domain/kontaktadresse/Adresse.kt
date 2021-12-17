package no.nav.personopplysninger.features.endreopplysningergammel.domain.kontaktadresse

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes(
    JsonSubTypes.Type(value = Vegadresse::class, name = "VEGADRESSE"),
    JsonSubTypes.Type(value = Postboksadresse::class, name = "POSTBOKSADRESSE"),
    JsonSubTypes.Type(value = UtenlandskAdresse::class, name = "UTENLANDSK_ADRESSE")
)
abstract class Adresse